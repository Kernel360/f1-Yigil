'use server';

import { cookies } from 'next/headers';

const BASE_URL =
  process.env.ENVIRONMENT === 'production'
    ? process.env.BASE_URL
    : process.env.NEXT_PUBLIC_API_MOCKING !== 'enabled'
    ? process.env.DEV_BASE_URL
    : typeof window === 'undefined'
    ? 'http://localhost:8080/api'
    : 'http://localhost:3000/api';

const headerInitOption = {
  'Content-Type': 'application/json',
};

export const requestWithoutCookie =
  (url: string) =>
  (params: string = '') =>
  (method: string = 'GET', body: any = null) =>
  (headers: Record<string, string> = headerInitOption) =>
  async (errorMsg: string = '요청에 실패했습니다') => {
    try {
      const res = await fetch(`${BASE_URL}/v1/${url}${params}`, {
        method,
        headers: {
          ...headerInitOption,
          ...headers,
        },
        next: { revalidate: 0 },

        ...(body && { body: JSON.stringify(body) }),
      });
      return res.json();
    } catch (error) {
      // error 컴포넌트나 토스트 정의 시 사용할 부분
      console.log(error); // 디버그 용
      console.error(errorMsg);
    }
  };

export const requestWithCookie =
  (url: string) =>
  (params: string = '') =>
  (method: string = 'GET', body: any = null) =>
  (headers: Record<string, string> = headerInitOption) =>
  async (errorMsg: string = '요청에 실패했습니다') => {
    const cookie = cookies().get('SESSION')?.value;

    try {
      const res = await fetch(`${BASE_URL}/v1/${url}${params}`, {
        method,
        headers: {
          ...headerInitOption,
          ...headers,
          Cookie: `SESSION=${cookie}`,
        },

        ...(body && { body: JSON.stringify(body) }),
      });

      return res.json();
    } catch (error) {
      // error 컴포넌트나 토스트 정의 시 사용할 부분
      console.log(error); // 디버그 용
      console.error(errorMsg);
    }
  };
