import z from 'zod';

import { requestWithoutCookie } from '@/app/_components/api/httpRequest';
import { placesSchema } from '@/types/response';

const requestPopularPlaces =
  requestWithoutCookie('places/popular')()()()(
    '장소들을 불러올 수 없었습니다!',
  );

const placeResponseSchema = z.object({
  places: placesSchema,
});

export async function getPopularPlaces() {
  const json = await requestPopularPlaces;

  const result = placeResponseSchema.safeParse(json);

  return result;
}
