import { NextRequest, NextResponse } from 'next/server';

export async function POST(request: NextRequest, response: NextResponse) {
  const { coords } = (await request.json()) as {
    coords: { lat: number; lng: number };
  };

  const lat = Number(coords.lat);
  const lng = Number(coords.lng);

  const result = await fetch(
    `https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=300&h=300&center=${lat},${lng}`,
    // 'https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=300&h=300&markers=type:n|size:small|color:0x029DFF|pos:127.1054221 037.3591614'
    {
      method: 'GET',
      headers: {
        'X-NCP-APIGW-API-KEY-ID': process.env.NEXT_PUBLIC_NAVER_MAPS_CLIENT_ID,
        'X-NCP-APIGW-API-KEY': process.env.MAP_SECRET,
      } as Record<string, string>,
    },
  );
  console.log(result);
  const res = await result.json();

  return Response.json(res);
}

//https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?w=300&h=300&markers=type:d|size:small|pos:127.1054221%2037.3591614
