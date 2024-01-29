import Link from 'next/link';
import React, {
  ComponentType,
  Dispatch,
  SetStateAction,
  useEffect,
} from 'react';

interface TPopOver {
  popOverData: {
    href: string;
    label: string;
    Icon: ComponentType<{ className?: string }>;
    onClick?: () => void;
  }[];
  setIsModalOpened: Dispatch<SetStateAction<boolean>>;
  position: string;
  style?: string;
  backDropStyle?: string;
}

export default function PopOver({
  popOverData,
  setIsModalOpened,
  position,
  style,
  backDropStyle,
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
      <div
        className={`fixed inset-0 max-w-[430px] mx-auto ${backDropStyle}`}
        onClick={() => setIsModalOpened(false)}
      ></div>
      <div
        className={`absolute bg-[#F3F4F6] rounded-md flex flex-col items-center justify-center ${position} ${style}`}
        onKeyDown={(e) =>
          (e.key === 'Esc' /** IE/Edge */ || e.key === 'Escape') &&
          setIsModalOpened(false)
        }
      >
        <div className="flex flex-col gap-5 justify-center items-center p-4">
          {popOverData.map(({ href, label, Icon, onClick }) => (
            <Link
              key={href}
              href={href}
              onClick={() => {
                onClick && onClick();
                setIsModalOpened(false);
              }}
              className="flex items-center"
            >
              <div>{label}</div>
              <Icon />
            </Link>
          ))}
        </div>
      </div>
    </>
  );
}
