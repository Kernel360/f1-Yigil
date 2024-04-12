import BackButton from '@/app/_components/place/BackButton';
import AreaList from '@/app/_components/setting/area/AreaList';
import { getAllArea } from '@/app/_components/setting/area/actions';
import React from 'react';

export default async function SetAreaPage() {
  const res = await getAllArea();

  if (res.status === 'failed') throw new Error(res.message);

  return (
    <>
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">지역 선택</span>
      </nav>
      <AreaList categories={res.data.categories} />
    </>
  );
}
