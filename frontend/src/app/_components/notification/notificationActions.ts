'use server';

import { getBaseUrl } from '@/app/utilActions';
import { notificationResponseSchema } from '@/types/notificationResponse';
import { TBackendRequestResult, backendErrorSchema } from '@/types/response';
import { parseResult } from '@/utils';
import { cookies } from 'next/headers';
import z from 'zod';

export async function getNotificationList(
  page: number = 1,
  size: number = 15,
  sortOption: string = 'desc',
): Promise<TBackendRequestResult<z.infer<typeof notificationResponseSchema>>> {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;

  const res = await fetch(
    `${BASE_URL}/v1/notifications?page=${page}&size=${size}&sortOrder=${sortOption}`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const result = await res.json();

  const error = backendErrorSchema.safeParse(result);
  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const notifications = parseResult(notificationResponseSchema, result);
  return notifications;
}
