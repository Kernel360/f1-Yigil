'use server';
import { cookies } from 'next/headers';

export const authenticateUser = async () => {
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${process.env.BASE_URL}/v1/members`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });
  return res.json();
};
