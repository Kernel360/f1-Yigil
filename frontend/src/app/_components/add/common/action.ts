'use server';

import { cookies } from 'next/headers';

import {
  requestWithoutCookie,
  requestWithCookie,
} from '@/app/_components/api/httpRequest';
import { blobTodataUrl, dataUrlToBlob, getMIMETypeFromDataURI } from '@/utils';

import {
  TBackendError,
  TPostSpotSuccess,
  backendErrorSchema,
  naverStaticMapUrlErrorSchema,
  postSpotResponseSchema,
  staticMapUrlSchema,
} from '@/types/response';
import type { TAddSpotProps } from '../spot/SpotContext';

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

const cookie = cookies().get('SESSION')?.value;
const backendStaticMapRequest = requestWithoutCookie('places/static-image');
const postSpotRequest = requestWithCookie('spots');

const getStaticMapUrlFromBackend = (name: string, address: string) => {
  return backendStaticMapRequest(`name=${name}&address=${address}`)()({
    Cookie: `SESSION=${cookie}`,
  })('First time adding');
};

export async function getMap(
  name: string,
  address: string,
  coords: { lat: number; lng: number },
): Promise<{ status: 'backend' | 'naver' | 'failed'; data?: string }> {
  const backendResponse = await getStaticMapUrlFromBackend(name, address);

  console.log(backendResponse);

  const backend = staticMapUrlSchema.safeParse(backendResponse);

  if (backend.success) {
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

  formData.append('title', JSON.stringify('Spot Review'));
  formData.append('isInCourse', JSON.stringify(false));
  formData.append('placeName', JSON.stringify(name));
  formData.append('placeAddress', JSON.stringify(address));
  formData.append('rate', JSON.stringify(rating));
  formData.append('description', JSON.stringify(review.review));

  /**
   * @todo GeoJson 쓰기
   */
  const pointJson = JSON.stringify({
    type: 'Point',
    coordinates: [coords.lng, coords.lat],
  });

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

  if (spotMapImageUrl.startsWith('data:')) {
    formData.append(
      'mapStaticImageFile',
      new File([dataUrlToBlob(spotMapImageUrl)], `${name} 지도 이미지`, {
        type: 'image/png',
      }),
    );
  }

  return formData;
}

export async function postSpotData(state: TAddSpotProps) {
  const formData = await parseAddSpotProps(state);

  const { ENVIRONMENT, BASE_URL, DEV_BASE_URL } = process.env;

  const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

  const json = await postSpotRequest()('POST', formData)()();

  // const response = await fetch(`${baseUrl}/v1/spots`, {
  //   method: 'POST',
  //   body: formData,
  //   headers: {
  //     Cookie: `SESSION=${cookie}`,
  //   },
  // });

  // if (!response.ok) {
  //   console.log(response.status);
  //   console.error('fetch failed');
  // }

  // const json = await response.json();

  const result = postSpotResponseSchema.safeParse(json);

  if (result.success) {
    return result;
  }

  const parsedError = backendErrorSchema.safeParse(json);

  return parsedError;
}
