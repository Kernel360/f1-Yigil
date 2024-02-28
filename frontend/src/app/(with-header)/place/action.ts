'use server';

import { requestWithoutCookie } from '@/app/_components/api/httpRequest';

export const requestPlace = (id: number) =>
  requestWithoutCookie(`places/${id}`)()()()(
    '장소 상세 정보를 가져오는 데 실패했습니다!',
  );
