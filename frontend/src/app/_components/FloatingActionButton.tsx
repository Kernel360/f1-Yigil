'use client';

import PlusIcon from '@/../public/icons/plus.svg';
import { useState } from 'react';
import PopOver from './ui/popover/PopOver';
import LocationIcon from '/public/icons/location.svg';
import CalendarIcon from '/public/icons/calendar.svg';

const popOverData = [
  { href: '/add/spot', label: '장소 추가하기', Icon: LocationIcon },
  { href: '/add/course', label: '일정 추가하기', Icon: CalendarIcon },
];

export default function FloatingActionButton() {
  const [modalOpened, setIsModalOpened] = useState(false);

  const closeModal = () => {
    setIsModalOpened(false);
  };

  return (
    <div className="w-min absolute right-20">
      <button
        className={`fixed bottom-4 w-16 h-16 rounded-full flex justify-center items-center  ${
          modalOpened ? 'bg-black cursor-auto' : 'bg-blue-600'
        }`}
        onClick={() => setIsModalOpened(!modalOpened)}
      >
        <PlusIcon
          className={`${
            modalOpened
              ? 'rotate-45 duration-200 z-30'
              : 'rotate-0 duration-200'
          } `}
        />
        {modalOpened && (
          <PopOver
            popOverData={popOverData}
            closeModal={closeModal}
            position="bottom-[70px] right-4"
            backDropStyle="bg-black bg-opacity-25"
            style="w-[160px] z-30"
          />
        )}
      </button>
    </div>
  );
}
