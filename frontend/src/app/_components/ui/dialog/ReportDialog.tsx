'use client';

import { useEffect, useState } from 'react';

import ViewPortal from '../../Portal';
import LoadingIndicator from '../../LoadingIndicator';

import XMarkIcon from '/public/icons/x-mark.svg';

import type { TReportType } from '@/types/response';
import ReportRadioGroup from '../ReportRadioGroup';

export default function ReportDialog({
  reportTypes,
  closeModal,
  handleConfirm,
}: {
  reportTypes?: TReportType[];
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
        className="absolute inset-0 m-auto w-2/3 h-fit bg-white rounded-xl flex flex-col"
        onClick={(event) => {
          event.stopPropagation();
        }}
        onKeyDown={(e) =>
          (e.key === 'Esc' /** IE/Edge */ || e.key === 'Escape') && closeModal()
        }
      >
        <div className="h-full relative grow">
          <div className="p-3 h-full flex flex-col gap-2">
            <div className="relative flex justify-end items-center">
              <span className="absolute w-fit inset-0 mx-auto text-2xl font-medium flex items-center">
                신고하기
              </span>
              <button className="p-2" onClick={closeModal}>
                <XMarkIcon className="w-6 h-6 stroke-gray-500" />
              </button>
            </div>
            <ReportRadioGroup reportTypes={reportTypes} />
            <div className="flex justify-end">
              <button
                className="w-1/4 py-1 text-lg rounded-lg bg-blue-500 text-white"
                onClick={handleClickConfirm}
              >
                제출
              </button>
            </div>
          </div>
          {isLoading && (
            <div className="absolute top-0 rounded-xl w-full h-full bg-black/25 z-30 flex justify-center items-center">
              <div className="bg-white rounded-lg w-2/3 h-2/3 flex justify-center items-center">
                <LoadingIndicator loadingText="신고중" style="rounded-xl" />
              </div>
            </div>
          )}
        </div>
      </div>
    </ViewPortal>
  );
}
