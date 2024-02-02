'use client';

import PlusIcon from '@/../public/icons/plus.svg';
import { useState } from 'react';

import LocationIcon from '/public/icons/location.svg';
import CalendarIcon from '/public/icons/calendar.svg';
import ViewPortal from './Portal';

const popOverData = [
  { href: '/add/spot', label: '장소 추가하기', Icon: LocationIcon },
  { href: '/add/course', label: '일정 추가하기', Icon: CalendarIcon },
];

export default function FloatingActionButton() {
  const [isModalOpened, setIsModalOpened] = useState(false);

  const closeModal = () => {
    setIsModalOpened(false);
  };

  return (
    <div className="w-min absolute right-24">
      <button
        className={`fixed bottom-20 w-16 h-16 rounded-full flex justify-center items-center  ${
          isModalOpened ? 'bg-black cursor-auto' : 'bg-blue-600'
        }`}
        onClick={() => setIsModalOpened(!isModalOpened)}
      >
        <PlusIcon
          className={`${
            isModalOpened
              ? 'rotate-45 duration-200 z-30'
              : 'rotate-0 duration-200'
          } `}
        />
        {isModalOpened && (
          <ViewPortal
            popOverData={popOverData}
            closeModal={closeModal}
            position="bottom-[160px] right-10"
            backdropStyle="bg-black bg-opacity-10"
          />
        )}
      </button>
    </div>
  );
}
