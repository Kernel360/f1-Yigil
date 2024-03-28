'use server';
import { getBaseUrl } from '@/app/utilActions';
import {
  TMapPlacesSchema,
  TMySpotIds,
  backendErrorSchema,
  mySpotIds,
} from '@/types/response';
import { cookies } from 'next/headers';

export const getNearPlaces = async (
  bounds: {
    maxX: number;
    maxY: number;
    minX: number;
    minY: number;
  },
  page: number,
) => {
  const BASE_URL = await getBaseUrl();
  const { maxX, maxY, minX, minY } = bounds;
  const res = await fetch(
    `${BASE_URL}/v1/places/near?minX=${minX}&minY=${minY}&maxX=${maxX}&maxY=${maxY}&page=${page}`,
  );
  const places = await res.json();
  const parsedPlaces = TMapPlacesSchema.safeParse(places);
  return parsedPlaces;
};

export const getMySpotIds = async (): Promise<
  | {
      status: 'failed';
      message: string;
    }
  | { status: 'success'; data: TMySpotIds }
> => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(`${BASE_URL}/v1/places/me`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });

  const result = await res.json();

  const error = backendErrorSchema.safeParse(result);

  if (error.success) {
    return { status: 'failed', message: error.data.message };
  }
  const parsedIds = mySpotIds.safeParse(result);

  if (!parsedIds.success) {
    return { status: 'failed', message: 'zod 검증 에러입니다.' };
  }
  return { status: 'success', data: parsedIds.data };
};
