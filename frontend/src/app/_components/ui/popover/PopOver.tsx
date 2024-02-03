import React, { ComponentType, useEffect } from 'react';
import PopOverIcon from './PopOverItem';

interface TPopOver {
  popOverData: {
    href: string;
    label: string;
    Icon: ComponentType<{ className?: string }>;
    onClick?: () => void;
  }[];
  closeModal: () => void;
  position?: string;
  style?: string;
}

export default function PopOver({
  popOverData,
  closeModal,
  position,
  style,
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
    <>
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
            popOverData.map(({ href, ...data }) => (
              <PopOverIcon
                key={href}
                href={href}
                {...data}
                closeModal={closeModal}
              />
            ))}
        </ul>
      </nav>
    </>
  );
}
