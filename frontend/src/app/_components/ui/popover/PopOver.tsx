import Link from 'next/link';
import React, {
  ComponentType,
  Dispatch,
  SetStateAction,
  useEffect,
} from 'react';

interface PropsType {
  popOverData: {
    href: string;
    label: string;
    Icon: ComponentType<{ className?: string }>;
    onClick?: () => void;
  }[];
  setIsModalOpened: Dispatch<SetStateAction<boolean>>;
}

export default function PopOver({ popOverData, setIsModalOpened }: PropsType) {
  const mouseEventPrevent = (e: Event) => {
    e.preventDefault();
  };

  useEffect(() => {
    window.addEventListener('DOMMouseScroll', mouseEventPrevent, false); // older FF
    window.addEventListener('wheel', mouseEventPrevent, { passive: false }); // modern desktop
    window.addEventListener('touchmove', mouseEventPrevent, { passive: false }); // mobile
    return () => {
      window.removeEventListener('DOMMouseScroll', mouseEventPrevent, false);
      window.removeEventListener('wheel', mouseEventPrevent, false);
      window.removeEventListener('touchmove', mouseEventPrevent);
    };
  }, []);
  return (
    <>
      <div
        className="fixed inset-0"
        onClick={() => setIsModalOpened(false)}
      ></div>
      <div className="absolute bottom-[-90px] right-4 w-[134px] h-[104px] bg-[#F3F4F6] rounded-md flex flex-col items-center justify-center gap-5">
        {popOverData.map(({ href, label, Icon, onClick }) => (
          <Link
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
    </>
  );
}
