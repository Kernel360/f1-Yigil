'use client';

import { useContext } from 'react';
import { AddSpotContext } from '../spot/SpotContext';
import { httpRequest } from '../../api/httpRequest';

// 이름과 주소를 입력으로 하여 static map 저장 여부를 반환하는 API가 필요함

// 외부 placeholder 이미지 사용중, no-img-element 린트 에러 발생
// 차후 next/image 사용하게 변경 예정
export default function AddPlaceInfo() {
  const { name, address, spotMapImageUrl } = useContext(AddSpotContext);

  return (
    <section className="p-4 flex flex-col gap-8">
      <div className="px-4 flex justify-between items-center">
        <span className="text-gray-500">이름</span>
        <span className="font-medium text-2xl">{name}</span>
      </div>
      <div className="px-4 flex justify-between items-center">
        <span className="text-gray-500">주소</span>
        <span className="font-medium">{address}</span>
      </div>
      <div className="w-full relative">
        <img src={spotMapImageUrl} alt="Example static map" />
      </div>
    </section>
  );
}
