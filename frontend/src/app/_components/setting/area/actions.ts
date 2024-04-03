'use server';

import { getBaseUrl } from '@/app/utilActions';
import { mypageAllAreaSchema } from '@/types/myPageResponse';
import { TBackendRequestResult } from '@/types/response';
import { parseResult } from '@/utils';
import { cookies } from 'next/headers';
import z from 'zod';

export async function getAllArea(): Promise<
  TBackendRequestResult<z.infer<typeof mypageAllAreaSchema>>
> {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/regions/select`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });
  const result = await res.json();

  const parsed = parseResult(mypageAllAreaSchema, result);
  return parsed;
}
