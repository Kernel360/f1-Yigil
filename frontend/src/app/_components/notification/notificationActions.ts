'use server';

import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';

export async function getNotifications() {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;

  const res = await fetch(`${BASE_URL}/v1/notifications`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });
  const result = await res.json();
  console.log(result);
}
