import React, { useState } from 'react';
import MyPagePlaceItem from './MyPagePlaceItem';

interface PropsType {
  placeList: {
    post_id: number;
    travel_id: number;
    title: string;
    imageUrl: string;
    description: string;
    isSecret: boolean;
    post_date: string;
  }[];
}

export default function MyPagePlaceList({ placeList }: PropsType) {
  const [selected, setSelected] = useState('');

  const onChangeSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelected(e.target.value);
  };
  console.log(placeList);
  // 필터링 처리 방법 백엔드 요청 | 프론트에서 처리
  return (
    <div>
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
      {placeList.map(
        ({
          imageUrl,
          post_id,
          travel_id,
          title,
          description,
          isSecret,
          post_date,
        }) => (
          <MyPagePlaceItem
            key={travel_id}
            travel_id={travel_id}
            imageUrl={imageUrl}
            title={title}
            description={description}
            post_id={post_id}
            isSecret={isSecret}
            post_date={post_date}
          />
        ),
      )}
    </div>
  );
}
