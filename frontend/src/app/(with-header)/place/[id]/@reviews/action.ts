import { getBaseUrl } from '@/app/utilActions';
import { spotSchema } from '@/types/response';
import { z } from 'zod';

const spotsResponseSchema = z.object({
  spots: z.array(spotSchema),
  has_next: z.boolean(),
});

async function fetchSpots(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'createdAt' | 'rate' = 'createdAt',
  sortOrder: 'desc' | 'asc' = 'desc',
) {
  const BASE_URL = await getBaseUrl();

  const endpoint = `${BASE_URL}/v1/spots/place/${placeId}`;
  const queryParams = Object.entries({ page, size, sortBy, sortOrder })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    next: { tags: ['spots'] },
  });

  return await response.json();
}

export async function getSpots(
  placeId: number,
  page: number = 1,
  size: number = 5,
  sortBy: 'createdAt' | 'rate' = 'createdAt',
  sortOrder: 'desc' | 'asc' = 'desc',
) {
  const json = await fetchSpots(placeId, page, size, sortBy, sortOrder);

  const result = spotsResponseSchema.safeParse(json);

  return result;
}
