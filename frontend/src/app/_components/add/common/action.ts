'use server';

import { cookies } from 'next/headers';

import {
  blobTodataUrl,
  coordsToGeoJSONPoint,
  dataUrlToBlob,
  getMIMETypeFromDataURI,
} from '@/utils';

import {
  backendErrorSchema,
  naverStaticMapUrlErrorSchema,
  postSpotResponseSchema,
  staticMapUrlSchema,
} from '@/types/response';

import type { TAddSpotProps } from '../spot/SpotContext';
import { revalidateTag } from 'next/cache';

function staticMapUrl(
  width: number,
  height: number,
  coords: {
    lat: number;
    lng: number;
  },
) {
  const { lat, lng } = coords;

  const endpoint = 'https://naveropenapi.apigw.ntruss.com/map-static/v2/raster';
  const queryString = `w=${width}&h=${height}&format=png&markers=type:d|size:mid|pos:${lng} ${lat}&scale=2`;

  return `${endpoint}?${queryString}`;
}

async function getStaticMapUrlFromBackend(name: string, address: string) {
  const cookie = cookies().get('SESSION')?.value;

  const { BASE_URL, DEV_BASE_URL, ENVIRONMENT } = process.env;
  const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

  const queryParams = `name=${name}&address=${address}`;

  const response = await fetch(
    `${baseUrl}/v1/places/static-image?${queryParams}`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );

  return await response.json();
}

export async function getMap(
  name: string,
  address: string,
  coords: { lat: number; lng: number },
): Promise<{ status: 'backend' | 'naver' | 'failed'; data?: string }> {
  const backendResponse = await getStaticMapUrlFromBackend(name, address);
  const backend = staticMapUrlSchema.safeParse(backendResponse);

  if (backend.success && backend.data.exists) {
    return { status: 'backend', data: backend.data.map_static_image_url };
  }

  const url = staticMapUrl(300, 200, coords);

  const naverResponse = await fetch(url, {
    method: 'GET',
    headers: {
      'X-NCP-APIGW-API-KEY-ID': process.env.NAVER_MAPS_CLIENT_ID,
      'X-NCP-APIGW-API-KEY': process.env.MAP_SECRET,
    },
  });

  if (!naverResponse.ok) {
    const json = await naverResponse.json();

    const parsedError = naverStaticMapUrlErrorSchema.safeParse(json.error);

    if (parsedError.success) {
      console.log(parsedError.data);
      console.error(parsedError.data);
    }

    return { status: 'failed' };
  }

  const image = await naverResponse.blob();

  // Server에서 Client로 넘겨줄 때 blob으로는 넘겨줄 수 없음
  const dataUrl = await blobTodataUrl(image);

  return { status: 'naver', data: dataUrl };
}

async function parseAddSpotProps(state: TAddSpotProps) {
  const { name, address, spotMapImageUrl, images, coords, rating, review } =
    state;

  const formData = new FormData();

  formData.append('title', 'Spot Review');
  formData.append('isInCourse', 'false');
  formData.append('placeName', name);
  formData.append('placeAddress', address);
  formData.append('rate', rating.toString());
  formData.append('description', review.review);

  const pointJson = coordsToGeoJSONPoint(coords);

  formData.append('placePointJson', pointJson);
  formData.append('pointJson', pointJson);

  const parsedImages = images.map(
    ({ filename, uri }) =>
      new File([dataUrlToBlob(uri)], filename, {
        type: getMIMETypeFromDataURI(uri),
      }),
  );

  formData.append('placeImageFile', parsedImages[0]);

  parsedImages.forEach((image) => formData.append('files', image));

  // If map image from Naver
  if (spotMapImageUrl.startsWith('data:')) {
    formData.append(
      'mapStaticImageFile',
      new File([dataUrlToBlob(spotMapImageUrl)], `${name} 지도 이미지.png`, {
        type: 'image/png',
      }),
    );
  }

  return formData;
}

export async function postSpotData(state: TAddSpotProps) {
  const session = cookies().get('SESSION')?.value;

  const formData = await parseAddSpotProps(state);

  const { ENVIRONMENT, BASE_URL, DEV_BASE_URL } = process.env;

  const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

  // Next.js fetch form은 'Content-Type': 'multipart/form-data를
  // 직접 명시하면 Boundary 설정이 어긋나 제대로 동작하지 않음
  const response = await fetch(`${baseUrl}/v1/spots`, {
    method: 'POST',
    body: formData,
    headers: {
      Cookie: `SESSION=${session}`,
    },
  });

  if (!response.ok) {
    console.log(response.status);
    console.error('fetch failed');
  }

  const json = await response.json();

  const result = postSpotResponseSchema.safeParse(json);

  if (result.success) {
    revalidateTag('popularPlaces');
    return result;
  }

  const parsedError = backendErrorSchema.safeParse(json);

  return parsedError;
}
