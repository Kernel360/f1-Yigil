import { getCoords } from '@/app/_components/naver-map/hooks/getCoords';
import { NextRequest, NextResponse } from 'next/server';

export async function POST(request: NextRequest, response: NextResponse) {
  const json = (await request.json()) as { address: string };
  const result = await getCoords(json.address);

  return Response.json(result);
}
