'use client';
import PlusIcon from '@/../public/icons/plus.svg';
import { useState } from 'react';
import { fabpopOverData } from './ui/popover/constants';
import PopOver from './ui/popover/PopOver';

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
          <PopOver
            popOverData={fabpopOverData}
            closeModal={closeModal}
            position="bottom-[160px] right-10"
            backdropStyle="bg-black bg-opacity-10"
          />
        )}
      </button>
    </div>
  );
}
