import BackButton from '@/app/_components/place/BackButton';
import AreaList from '@/app/_components/setting/area/AreaList';
import { getAllArea } from '@/app/_components/setting/area/actions';
import ToastMsg from '@/app/_components/ui/toast/ToastMsg';
import { mypageAllAreaSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function SetAreaPage() {
  const res = await getAllArea();
  const categories = mypageAllAreaSchema.safeParse(res);
  if (!categories.success)
    return (
      <>
        <nav className="relative py-4 flex justify-center items-center">
          <BackButton className="absolute left-2" />
          <span className="text-2xl font-light">지역 선택</span>
        </nav>
        <div className="w-full h-full flex flex-col break-keep justify-center items-center text-3xl text-center text-main">
          지역 정보를 불러오는데 실패했습니다. <br />
          다시 시도해주세요.
        </div>
      </>
    );

  return (
    <>
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">지역 선택</span>
      </nav>
      <AreaList categories={categories.data.categories} />
    </>
  );
}
