'use server';

import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';

export async function getNotificationList(
  page: number = 1,
  size: number = 5,
  sortOption: string = 'desc',
) {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;

  const res = await fetch(
    `${BASE_URL}/v1/notifications?page=${page}&size=${size}`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const result = await res.json();
  return result;
}
