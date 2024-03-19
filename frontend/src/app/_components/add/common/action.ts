'use server';

import { cookies } from 'next/headers';

import { blobTodataUrl } from '@/utils';

import {
  naverStaticMapUrlErrorSchema,
  staticMapUrlSchema,
} from '@/types/response';

import { getBaseUrl } from '@/app/utilActions';

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

  const BASE_URL = await getBaseUrl();

  const queryParams = `name=${name}&address=${address}`;

  const response = await fetch(
    `${BASE_URL}/v1/places/static-image?${queryParams}`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );

  return await response.json();
}

type TMapImage =
  | { status: 'succeed'; data: string }
  | { status: 'failed'; error: string };

export async function getMap(
  name: string,
  address: string,
  coords: { lat: number; lng: number },
): Promise<TMapImage> {
  const backendResponse = await getStaticMapUrlFromBackend(name, address);
  const backend = staticMapUrlSchema.safeParse(backendResponse);

  if (backend.success) {
    if (backend.data.registered_place) {
      return { status: 'failed', error: '이미 리뷰를 작성한 장소입니다!' };
    }

    if (backend.data.exists) {
      if (!backend.data.map_static_image_url) {
        return { status: 'failed', error: '알 수 없는 에러입니다!' };
      }
      return { status: 'succeed', data: backend.data.map_static_image_url };
    }
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

    return {
      status: 'failed',
      error: '검색 엔진으로부터 지도를 받아올 수 없습니다!',
    };
  }

  const image = await naverResponse.blob();

  // Server에서 Client로 넘겨줄 때 blob으로는 넘겨줄 수 없음
  const dataUrl = await blobTodataUrl(image);

  return { status: 'succeed', data: dataUrl };
}
