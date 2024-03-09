import BackButton from '@/app/_components/place/BackButton';
import React from 'react';
import { getAllArea } from '../../_components/setting/area/actions';
import { mypageAllAreaSchema } from '@/types/myPageResponse';
import AreaList from '@/app/_components/setting/area/AreaList';

export default async function AreaPage() {
  // 내 관심 지역 불러오는 함수
  // 전체 지역 불러오는 함수

  const res = await getAllArea();
  const allAreas = mypageAllAreaSchema.safeParse(res);

  if (!allAreas.success)
    return <main>지역 정보를 불러오는데 실패했습니다.</main>;
  return (
    <div className="max-w-[430px]">
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">지역 선택</span>
      </nav>
      <div className="mx-4">
        <AreaList {...allAreas?.data} />
      </div>
    </div>
  );
}
