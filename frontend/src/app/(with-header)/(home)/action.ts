import z from 'zod';

import { placesSchema } from '@/types/response';
import { getBaseUrl } from '@/app/utilActions';

async function fetchMorePopularPlaces() {
  const BASE_URL = await getBaseUrl();

  const response = await fetch(`${BASE_URL}/v1/places/popular`);

  return await response.json();
}

async function fetchPopularPlaces() {
  const BASE_URL = await getBaseUrl();

  const response = await fetch(`${BASE_URL}/v1/places/popular`, {
    next: { tags: ['popularPlaces'] },
  });

  return await response.json();
}

const placeResponseSchema = z.object({
  places: placesSchema,
});

export async function getPopularPlaces() {
  const json = await fetchPopularPlaces();

  const result = placeResponseSchema.safeParse(json);

  return result;
}

export async function getMorePopularPlaces() {
  const json = await fetchMorePopularPlaces();

  const result = placeResponseSchema.safeParse(json);

  return result;
}
