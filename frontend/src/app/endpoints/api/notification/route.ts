import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';
import { NextRequest, NextResponse } from 'next/server';

export const dynamic = 'force-dynamic';

export async function GET(req: NextRequest) {
  const cookie = cookies().get('SESSION')?.value;
  const BASE_URL = await getBaseUrl();

  // try {
  const response = await fetch(`${BASE_URL}/v1/notifications/stream`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
      'Content-Type': 'text/event-stream',
      Connection: 'keep-alive',
    },
  });
  console.log(await response.json());
  // const body = response.body;
  // return new NextResponse(body, {
  //   status: 200,
  //   headers: {
  //     'Access-Control-Allow-Origin': 'origin' || '*',
  //     'Content-Type': 'text/event-stream; charset=utf-8;',
  //     'Cache-Control': 'no-cache, no-transform',
  //     Connection: 'keep-alive',
  //     'Content-Encoding': 'none',
  //   },
  // });
  // console.log(response);
  return response;
  // } catch (error) {
  //   console.log(error);
  //   return new NextResponse('Internal Server Error', { status: 500 });
  // }
}
