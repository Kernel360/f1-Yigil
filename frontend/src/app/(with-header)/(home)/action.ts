'use server';

import z from 'zod';

import { cookies } from 'next/headers';
import { placeSchema, regionSchema } from '@/types/response';
import { getBaseUrl } from '@/app/utilActions';

const placeResponseSchema = z.object({
  places: z.array(placeSchema),
});

const regionResponseSchema = z.object({
  regions: z.array(regionSchema),
});

async function fetchPopularPlaces(more?: 'more') {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const endpoint = `${BASE_URL}/v1/places/popular${
    more === 'more' ? '/more' : ''
  }`;

  const response = await fetch(endpoint, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
    next: { tags: ['popularPlaces'] },
  });

  return await response.json();
}

async function fetchInterestedRegions() {
  const BASE_URL = await getBaseUrl();

  const session = cookies().get('SESSION')?.value;

  const response = await fetch(`${BASE_URL}/v1/regions/my`, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
    next: { tags: ['interestedRegions'] },
  });

  return await response.json();
}

async function fetchRegionPlaces(id: number) {
  const BASE_URL = await getBaseUrl();

  const response = await fetch(`${BASE_URL}/v1/places/region/${id}`, {
    next: { tags: ['regionPlaces'] },
  });

  return await response.json();
}

export async function getPopularPlaces(more?: 'more') {
  const json = await fetchPopularPlaces(more);

  const result = placeResponseSchema.safeParse(json);

  return result;
}

export async function getInterestedRegions() {
  const json = await fetchInterestedRegions();

  const result = regionResponseSchema.safeParse(json);

  return result;
}

export async function getRegionPlaces(id: number) {
  const json = await fetchRegionPlaces(id);

  const result = placeResponseSchema.safeParse(json);

  return result;
}
