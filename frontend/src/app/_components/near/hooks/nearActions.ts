'use server';
import { getBaseUrl } from '@/app/utilActions';
import {
  TBackendRequestResult,
  TMapPlacesSchema,
  mySpotIds,
} from '@/types/response';
import { parseResult } from '@/utils';
import { cookies } from 'next/headers';
import z from 'zod';

export const getNearPlaces = async (
  bounds: {
    maxX: number;
    maxY: number;
    minX: number;
    minY: number;
  },
  page: number,
): Promise<TBackendRequestResult<z.infer<typeof TMapPlacesSchema>>> => {
  const BASE_URL = await getBaseUrl();
  const { maxX, maxY, minX, minY } = bounds;
  const res = await fetch(
    `${BASE_URL}/v1/places/near?minX=${minX}&minY=${minY}&maxX=${maxX}&maxY=${maxY}&page=${page}`,
  );
  const places = await res.json();

  const parsedPlaces = parseResult(TMapPlacesSchema, places);
  return parsedPlaces;
};

export const getMySpotIds = async (): Promise<
  TBackendRequestResult<z.infer<typeof mySpotIds>>
> => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const res = await fetch(`${BASE_URL}/v1/places/me`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });

  const result = await res.json();

  const parsedIds = parseResult(mySpotIds, result);
  return parsedIds;
};
