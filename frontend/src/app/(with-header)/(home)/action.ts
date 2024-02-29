import { requestWithoutCookie } from '@/app/_components/api/httpRequest';

import { placesSchema } from '@/types/response';
import { revalidatePath } from 'next/cache';
import z from 'zod';

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
  revalidatePath('/', 'layout');

  return result;
}
