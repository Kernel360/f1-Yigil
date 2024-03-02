import z from 'zod';

import { placesSchema } from '@/types/response';

async function fetchPopularPlaces() {
  const { BASE_URL, DEV_BASE_URL, ENVIRONMENT } = process.env;

  const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

  const response = await fetch(`${baseUrl}/v1/places/popular`, {
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
