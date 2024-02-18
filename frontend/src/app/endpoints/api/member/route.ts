import { httpRequest } from '@/app/_components/api/httpRequest';
import { clearPreviewData } from 'next/dist/server/api-utils/index';
import { NextRequest, NextResponse } from 'next/server';

export async function GET(request: NextRequest, response: NextResponse) {
  const cookie = request.headers
    .get('cookie')
    ?.split('SESSION=')[1]
    .split(';')[0];

  const res = await fetch('https://yigil.co.kr/api/v1/members', {
    method: 'GET',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });
  const result = await res.json();

  return NextResponse.json(result);
}
