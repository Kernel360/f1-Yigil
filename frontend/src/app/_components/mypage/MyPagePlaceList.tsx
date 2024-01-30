import React, { useState } from 'react';
import MyPagePlaceItem, { TMyPagePlaceItem } from './MyPagePlaceItem';

export default function MyPagePlaceList({
  placeList,
}: {
  placeList: TMyPagePlaceItem[];
}) {
  const [selected, setSelected] = useState('');

  const onChangeSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelected(e.target.value);
  };
  // 필터링 처리 방법 백엔드 요청 | 프론트에서 처리
  return (
    <>
      <div className="flex justify-end">
        <select
          className="mr-4 pr-1 text-sm"
          name="place-filter"
          id="place-select"
          onChange={onChangeSelect}
        >
          <option value="최신순">최신순</option>
          <option value="거리순">거리순</option>
        </select>
      </div>
      {placeList.map(({ travel_id, ...data }) => (
        <MyPagePlaceItem key={travel_id} travel_id={travel_id} {...data} />
      ))}
    </>
  );
}
