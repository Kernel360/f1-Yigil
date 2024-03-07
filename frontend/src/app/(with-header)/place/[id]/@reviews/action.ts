import { z } from 'zod';

import { getBaseUrl } from '@/app/utilActions';
import { spotSchema } from '@/types/response';

const spotsResponseSchema = z.object({
  spots: z.array(spotSchema),
  has_next: z.boolean(),
});

async function fetchSpots(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'created_at' | 'rate',
  sortOrder: 'desc' | 'asc',
) {
  const BASE_URL = await getBaseUrl();

  const endpoint = `${BASE_URL}/v1/spots/place/${placeId}`;
  const queryParams = Object.entries({ page, size, sortBy, sortOrder })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    next: { tags: [`spots/${placeId}`] },
    cache: 'no-store',
  });

  console.log(`fetchSpot action: spots/${placeId}`);

  return await response.json();
}

export async function getSpots(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'created_at' | 'rate' = 'created_at',
  sortOrder: 'desc' | 'asc' = 'desc',
) {
  const json = await fetchSpots(placeId, page, size, sortBy, sortOrder);

  // for debug
  console.log(Date.now());
  console.log(json.spots[0].liked);

  const result = spotsResponseSchema.safeParse(json);

  return result;
}
