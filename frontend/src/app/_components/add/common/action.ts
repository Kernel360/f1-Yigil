'use server';

import { cookies } from 'next/headers';

import {
  requestWithoutCookie,
  requestWithCookie,
} from '@/app/_components/api/httpRequest';
import { blobTodataUrl } from '@/utils';
import {
  backendErrorSchema,
  naverStaticMapUrlErrorSchema,
  postSpotResponseSchema,
  staticMapUrlSchema,
} from '@/types/response';

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

export async function postSpotData(formData: FormData) {
  const json = await postSpotRequest()('POST', formData)({})();

  const result = postSpotResponseSchema.safeParse(json);

  if (result.success) {
    console.log(result.data);
    return;
  }

  const parsedError = backendErrorSchema.safeParse(json);

  if (parsedError.success) {
    console.log(parsedError.data);
    console.error(parsedError.data);
    return;
  }

  console.log(parsedError.error);
  console.error(parsedError.error);
}
