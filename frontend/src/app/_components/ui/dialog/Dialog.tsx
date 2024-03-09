'use client';

import { useEffect, useState } from 'react';

import ViewPortal from '../../Portal';
import LoadingIndicator from '../../LoadingIndicator';

export default function Dialog({
  text,
  loadingText,
  closeModal,
  handleConfirm,
}: {
  text: string;
  loadingText?: string;
  closeModal: () => void;
  handleConfirm: () => Promise<void>;
}) {
  const [isLoading, setIsLoading] = useState(false);

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

  async function handleClickConfirm() {
    setIsLoading(true);
    await handleConfirm();
    setIsLoading(false);
  }

  return (
    <ViewPortal closeModal={closeModal} backdropStyle="bg-black bg-opacity-10">
      <div
        className="absolute inset-0 m-auto w-2/3 h-1/4 bg-white rounded-xl flex flex-col"
        onClick={(event) => {
          event.stopPropagation();
        }}
        onKeyDown={(e) =>
          (e.key === 'Esc' /** IE/Edge */ || e.key === 'Escape') && closeModal()
        }
      >
        <div className="h-full relative">
          <div className="p-4 h-full flex flex-col">
            <div className="flex justify-center items-center grow">{text}</div>
            <div className="flex justify-between gap-2">
              <button
                className="w-1/2 py-2 rounded-lg bg-main"
                onClick={handleClickConfirm}
              >
                예
              </button>
              <button
                className="w-1/2 py-2 rounded-lg bg-gray-300"
                onClick={closeModal}
              >
                아니오
              </button>
            </div>
          </div>
          {isLoading && (
            <div className="absolute top-0 rounded-xl w-full h-full bg-black/25 z-30 flex justify-center items-center">
              <div className="bg-white rounded-lg w-2/3 h-2/3 flex justify-center items-center">
                <LoadingIndicator
                  loadingText={loadingText || ''}
                  style="rounded-xl"
                />
              </div>
            </div>
          )}
        </div>
      </div>
    </ViewPortal>
  );
}
