import React, { useEffect } from 'react';
import ViewPortal from '../../Portal';
import PopOverIcon from './PopOverItem';
import { TPopOverData } from './types';

interface TPopOver {
  popOverData: TPopOverData[];
  closeModal: () => void;
  position?: string;
  style?: string;
  backdropStyle?: string;
}

export default function PopOver({
  popOverData,
  closeModal,
  position,
  style,
  backdropStyle,
}: TPopOver) {
  const mouseEventPrevent = (e: Event) => {
    e.preventDefault();
  };
  const keyBoardArrowPrevent = (e: KeyboardEvent) => {
    if (e.key === 'ArrowDown' || e.key === 'ArrowUp') e.preventDefault();
  };

  useEffect(() => {
    window.addEventListener('DOMMouseScroll', mouseEventPrevent, false); // older FF
    window.addEventListener('wheel', mouseEventPrevent, { passive: false }); // modern desktop
    window.addEventListener('touchmove', mouseEventPrevent, { passive: false }); // mobile
    window.addEventListener('keydown', keyBoardArrowPrevent);
    return () => {
      window.removeEventListener('DOMMouseScroll', mouseEventPrevent, false);
      window.removeEventListener('wheel', mouseEventPrevent, false);
      window.removeEventListener('touchmove', mouseEventPrevent);
      window.removeEventListener('keydown', keyBoardArrowPrevent);
    };
  }, []);
  return (
    <ViewPortal closeModal={closeModal} backdropStyle={backdropStyle}>
      <nav
        className={`absolute bg-[#F3F4F6] rounded-md flex flex-col items-center justify-center ${position} ${
          style ? style : 'z-0'
        } `}
        onKeyDown={(e) =>
          (e.key === 'Esc' /** IE/Edge */ || e.key === 'Escape') && closeModal()
        }
      >
        <ul className="flex flex-col gap-5 justify-center items-center p-4">
          {popOverData &&
            popOverData.map((data, idx) => (
              <PopOverIcon key={idx} data={data} closeModal={closeModal} />
            ))}
        </ul>
      </nav>
    </ViewPortal>
  );
}
