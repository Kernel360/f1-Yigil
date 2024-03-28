'use client';

import { useState } from 'react';
import PopOver from './ui/popover/PopOver';

import type { ReactElement } from 'react';
import type { TPopOverData } from './ui/popover/types';

interface TFloatingActionButton {
  popOverData: TPopOverData[];
  backdropStyle?: string;
  openedIcon: ReactElement;
  closedIcon: ReactElement;
}

export default function FloatingActionButton({
  popOverData,
  backdropStyle,
  openedIcon,
  closedIcon,
}: TFloatingActionButton) {
  const [isModalOpened, setIsModalOpened] = useState(false);

  const closeModal = () => {
    setIsModalOpened(false);
  };

  return (
    <div className="w-min absolute right-24">
      <button
        className={`fixed bottom-20 w-16 h-16 rounded-full flex justify-center items-center bg-black ${
          isModalOpened ? 'cursor-auto' : ''
        }`}
        onClick={() => setIsModalOpened(!isModalOpened)}
      >
        {isModalOpened ? openedIcon : closedIcon}
        {isModalOpened && (
          <PopOver
            popOverData={popOverData}
            closeModal={closeModal}
            position="bottom-[160px] right-10"
            backdropStyle={backdropStyle}
          />
        )}
      </button>
    </div>
  );
}
