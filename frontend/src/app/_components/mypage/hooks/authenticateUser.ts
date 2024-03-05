'use server';
import { cookies } from 'next/headers';
const { ENVIRONMENT, BASE_URL, DEV_BASE_URL } = process.env;
const baseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;

export const authenticateUser = async () => {
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${baseUrl}/v1/members`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });
  return res.json();
};
