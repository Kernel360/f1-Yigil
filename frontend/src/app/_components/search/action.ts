'use server';

import { cookies } from 'next/headers';
import { getBaseUrl } from '@/app/utilActions';
import { dataWithAddressSchema, searchItemsSchema } from '@/types/response';

import { searchPlaceSchema } from '@/context/search/reducer';

function searchUrl(keyword: string) {
  const endpoint = 'https://openapi.naver.com/v1/search/local.json';
  const queryString = `query=${keyword}&display=10&sort=random`;

  return `${endpoint}?${queryString}`;
}

function coordsUrl(address: string) {
  const endpoint =
    'https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode';
  const queryString = `query=${address}`;

  return `${endpoint}?${queryString}`;
}

interface TSearchResult {
  status: 'failed' | 'succeed';
  data: Array<{ name: string; roadAddress: string }>;
}

type TSearchNaverResult =
  | { status: 'failed'; message: string }
  | { status: 'succeed'; data: Array<{ name: string; roadAddress: string }> };

export async function searchNaverAction(
  keyword: string,
): Promise<TSearchNaverResult> {
  const response = await fetch(searchUrl(keyword), {
    method: 'GET',
    headers: {
      'X-Naver-Client-Id': process.env.NAVER_SEARCH_ID,
      'X-Naver-Client-Secret': process.env.NAVER_SEARCH_SECRET,
    },
  });

  const json = await response.json();
  const body = json.items;

  const items = searchItemsSchema.safeParse(body);

  if (!items.success) {
    console.log(items.error.errors);
    return { status: 'failed', message: items.error.message };
  }

  const result = items.data.map(({ title, roadAddress }) => {
    const name = title.replace(/<b>|<\/b>/g, '');

    return { name, roadAddress };
  });

  return { status: 'succeed', data: result };
}

export async function searchAction(keyword: string): Promise<TSearchResult> {
  const response = await fetch(searchUrl(keyword), {
    method: 'GET',
    headers: {
      'X-Naver-Client-Id': process.env.NAVER_SEARCH_ID,
      'X-Naver-Client-Secret': process.env.NAVER_SEARCH_SECRET,
    },
  });

  const json = await response.json();
  const body = json.items;

  const items = searchItemsSchema.safeParse(body);

  if (!items.success) {
    console.log(items.error.errors);
    return { status: 'failed', data: [] };
  }

  const result = items.data.map(({ title, roadAddress }) => {
    const name = title.replace(/<b>|<\/b>/g, '');

    return { name, roadAddress };
  });

  return { status: 'succeed', data: result };
}

export async function getCoords(
  roadAddress: string,
): Promise<
  | { status: 'failed'; message: string }
  | { status: 'succeed'; content: { lng: number; lat: number } }
> {
  const response = await fetch(coordsUrl(roadAddress), {
    method: 'GET',
    headers: {
      'X-NCP-APIGW-API-KEY-ID': process.env.NAVER_MAPS_CLIENT_ID,
      'X-NCP-APIGW-API-KEY': process.env.MAP_SECRET,
    },
  });

  const json = await response.json();

  const data = json.addresses;

  const coords = dataWithAddressSchema.safeParse(data);

  if (!coords.success) {
    return { status: 'failed', message: '' };
  }

  const lng = Number.parseFloat(coords.data[0].x);
  const lat = Number.parseFloat(coords.data[0].y);

  return { status: 'succeed', content: { lng, lat } };
}

export async function searchPlaces(
  keyword: string,
  page: number = 1,
  size: number = 5,
  sortBy: 'latest_uploaded_time' | 'rate' = 'latest_uploaded_time',
  sortOrder: 'desc' | 'asc' = 'desc',
) {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const endpoint = `${BASE_URL}/v1/places/search`;
  const queryParams = Object.entries({ keyword, page, size, sortBy, sortOrder })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    headers: {
      Cookie: `SESSION=${session}`,
    },
    next: { tags: [`search/places/${keyword}`] },
  });

  return await response.json();
}

export async function keywordSuggestions(keyword: string) {
  const BASE_URL = await getBaseUrl();

  const response = await fetch(
    `${BASE_URL}/v1/places/keyword?keyword=${keyword}`,
    {
      next: { tags: [`keywords/${keyword}`] },
    },
  );

  return await response.json();
}
