import { useEffect } from 'react';

import ViewPortal from '../../Portal';

export default function Alert({
  text,
  closeModal,
}: {
  text: string;
  closeModal: () => void;
}) {
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
    <ViewPortal closeModal={closeModal} backdropStyle="bg-black bg-opacity-10">
      <div
        className="p-4 absolute inset-0 m-auto w-2/3 h-1/4 bg-white rounded-xl flex flex-col"
        onClick={(event) => {
          event.stopPropagation();
        }}
        onKeyDown={(e) =>
          (e.key === 'Esc' /** IE/Edge */ || e.key === 'Escape') && closeModal()
        }
      >
        <div className="flex justify-center items-center grow">{text}</div>
        <div className="flex">
          <button
            className="py-2 w-full rounded-lg bg-main"
            onClick={closeModal}
          >
            확인
          </button>
        </div>
      </div>
    </ViewPortal>
  );
}
