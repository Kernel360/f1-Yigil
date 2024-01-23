'use client';
import React, { useState } from 'react';
import { myPageTabs } from './constants';
import MyPageMyPlace from './MyPlace';
import MyPageStoredPlace from './StoredPlace';

export default function MyPageContent() {
  const [tabState, setTabState] = useState(0);

  return (
    <>
      <div className="flex items-center">
        {myPageTabs.map((tab, idx) => (
          <div
            key={idx}
            className={`w-[214px] h-[64px] flex justify-center items-center text-2xl cursor-pointer ${
              tabState === idx
                ? 'text-black border-b-2 border-black'
                : 'text-gray-300 border-b-2 border-white'
            }`}
            onClick={() => setTabState(idx)}
          >
            {tab}
          </div>
        ))}
      </div>
      {tabState === 0 ? (
        <div className="mt-3">
          <MyPageMyPlace />
        </div>
      ) : (
        <div className="mt-3">
          <MyPageStoredPlace />
        </div>
      )}
    </>
  );
}
