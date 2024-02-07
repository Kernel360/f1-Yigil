import { getArea } from '@/app/_components/naver-map/hooks/getArea';
import { NextRequest, NextResponse } from 'next/server';

export async function POST(request: NextRequest, response: NextResponse) {
  const json = (await request.json()) as { keyword: string };
  const result = await getArea(json.keyword);

  return Response.json(result);
}
