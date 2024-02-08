import { TAddSpotProps } from '@/app/_components/add/spot/SpotContext';
import { NextRequest, NextResponse } from 'next/server';

export async function POST(request: NextRequest, response: NextResponse) {
  const { name, address, spotMapImageUrl, images, coords, rating, review } =
    (await request.json()) as TAddSpotProps;

  const cookie = request.headers
    .get('cookie')
    ?.split('SESSION=')[1]
    .split(';')[0];

  const postBody = JSON.stringify({
    title: name,
    point_json: coords,
    description: review,
    files: images,
    place_point_json: coords,
    place_image_url: spotMapImageUrl,
    map_static_image_url: spotMapImageUrl,
    rate: rating,
    place_name: name,
    place_address: address,
  });

  const res = await fetch('https://yigil.co.kr/api/v1/spots', {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
    body: postBody,
  });
  const result = await res.json();
  console.log(result);
}
