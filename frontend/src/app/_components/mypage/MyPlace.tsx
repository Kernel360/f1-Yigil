'use client';
import React, { useEffect, useState } from 'react';
import { myPageMyPlaceTab } from './constants';
import MyPagePlaceList from './MyPagePlaceList';

export default function MyPageMyPlace() {
  const [tabState, setTabState] = useState(0);
  const [placeList, setPlaceList] = useState([]);

  const getData = async () => {
    const res = await fetch('http://localhost:8080/api/my/posts', {
      method: 'GET',
      headers: {
        Authorization: 'Bearer token',
      },
    });
    const result = await res.json();
    setPlaceList(result.data);
  };
  useEffect(() => {
    /**tab state에 따라 장소를 가져올 함수 호출할 지 일정을 가져올 함수 호출할 지 */
    getData();
  }, [tabState]);

  return (
    <>
      <div className="items-center grid grid-cols-4">
        {myPageMyPlaceTab.map((place, idx) => (
          <div
            key={idx}
            tabIndex={0}
            className={`py-[14px] flex justify-center text-xl leading-6 cursor-pointer ${
              tabState !== idx && 'text-gray-300 bg-gray-200'
            } `}
            onClick={() => setTabState(idx)}
          >
            {place}
          </div>
        ))}
      </div>

      <div className="mt-3">
        <MyPagePlaceList placeList={placeList} />
      </div>
    </>
  );
}
