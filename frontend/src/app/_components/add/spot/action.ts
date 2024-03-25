'use server';

import { cookies } from 'next/headers';
import { revalidateTag } from 'next/cache';
import { getBaseUrl } from '@/app/utilActions';
import {
  coordsToGeoJSONPoint,
  dataUrlToBlob,
  getMIMETypeFromDataURI,
} from '@/utils';

import type { TSpotState } from '@/context/travel/schema';
import { backendErrorSchema, postSpotResponseSchema } from '@/types/response';

async function parseAddSpotState(state: TSpotState) {
  const { place, images, review } = state;

  const formData = new FormData();

  formData.append('title', 'Spot Review');
  formData.append('placeName', place.name);
  formData.append('placeAddress', place.address);
  formData.append('rate', review.rate.toString());
  formData.append('description', review.content);

  const pointJson = coordsToGeoJSONPoint(place.coords);

  formData.append('placePointJson', pointJson);
  formData.append('pointJson', pointJson);

  const parsedImages =
    images.type === 'new'
      ? images.data.map(
          ({ filename, uri }) =>
            new File([dataUrlToBlob(uri)], filename, {
              type: getMIMETypeFromDataURI(uri),
            }),
        )
      : [];

  formData.append('placeImageFile', parsedImages[0]);
  parsedImages.forEach((image) => formData.append('files', image));

  // If map image from Naver
  if (place.mapImageUrl.startsWith('data:')) {
    formData.append(
      'mapStaticImageFile',
      new File(
        [dataUrlToBlob(place.mapImageUrl)],
        `${place.name} 지도 이미지.png`,
        {
          type: 'image/png',
        },
      ),
    );
  }

  return formData;
}

export async function postSpot(
  state: TSpotState,
): Promise<{ status: 'succeed' } | { status: 'failed'; message: string }> {
  const formData = await parseAddSpotState(state);
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const response = await fetch(`${BASE_URL}/v1/spots`, {
    method: 'POST',
    body: formData,
    headers: {
      Cookie: `SESSION=${session}`,
    },
  });

  if (response.status.toString()[0] === '5') {
    return { status: 'failed', message: '서버와 연결할 수 없습니다!' };
  }

  const json = await response.json();

  const error = backendErrorSchema.safeParse(json);

  if (!error.success) {
    const result = postSpotResponseSchema.safeParse(json);

    if (!result.success) {
      return { status: 'failed', message: '알 수 없는 에러입니다!' };
    }

    // 새로고침할 태그 꼭 확인하기
    revalidateTag('popularPlaces');

    return { status: 'succeed' };
  }

  return {
    status: 'failed',
    message: `${error.data.code} - ${error.data.message}`,
  };
}
