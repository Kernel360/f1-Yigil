'use server';

import { requestWithoutCookie } from '@/app/_components/api/httpRequest';
import { placeDetailSchema } from '@/types/response';

export const requestPlaceDetail = (id: number) =>
  requestWithoutCookie(`places/${id}`)()()()(
    '장소 상세 정보를 가져오는 데 실패했습니다!',
  );

export async function getPlaceDetail(idString: string) {
  const id = Number.parseInt(idString, 10);

  const json = await requestPlaceDetail(id);

  const result = placeDetailSchema.safeParse(json);

  return result;
}
