'use server';

import { mySpotForPlaceSchema, placeDetailSchema } from '@/types/response';
import { cookies } from 'next/headers';

async function fetchPlaceDetail(id: number) {
  const session = cookies().get('SESSION')?.value;

  const { BASE_URL, DEV_BASE_URL, ENVIRONMENT } = process.env;

  const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

  const response = await fetch(`${baseUrl}/v1/places/${id}`, {
    headers: {
      Cookies: `SESSION=${session}`,
    },
  });

  return await response.json();
}

async function fetchMySpotForPlace(id: number) {
  const session = cookies().get('SESSION')?.value;

  const { BASE_URL, DEV_BASE_URL, ENVIRONMENT } = process.env;

  const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

  const response = await fetch(`${baseUrl}/v1/spots/place/${id}/me`, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
  });

  return await response.json();
}

export async function getPlaceDetail(idString: string) {
  const id = Number.parseInt(idString, 10);

  const json = await fetchPlaceDetail(id);

  const result = placeDetailSchema.safeParse(json);

  return result;
}

export async function getMySpotForPlace(idString: string) {
  const id = Number.parseInt(idString, 10);

  const json = await fetchMySpotForPlace(id);

  const result = mySpotForPlaceSchema.safeParse(json);

  return result;
}
