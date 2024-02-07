import { useContext, useState } from 'react';
import { AddSpotContext } from '../../spot/SpotContext';

import XMarkIcon from '/public/icons/x-mark.svg';
import Dialog from '@/app/_components/ui/dialog/Dialog';

import type { EventFor } from '@/types/type';
import type { TStep } from './types';

function dataUrlToBlob(dataURI: string) {
  const byteString = atob(dataURI.split(',')[1]);

  // separate out the mime component
  const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

  // write the bytes of the string to an ArrayBuffer
  const ab = new ArrayBuffer(byteString.length);
  const ia = new Uint8Array(ab);
  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ab], { type: mimeString });
}

/**
 * `next` - 상위 컴포넌트에서 `dispatch({ type: 'next' })`를 감싼 이벤트 핸들러
 *
 * `previous` - 상위 컴포넌트에서 `dispatch({ type: 'previous' })`를 감싼 이벤트 핸들러
 */
export default function StepNavigation({
  currentStep,
  next,
  previous,
}: {
  currentStep: TStep;
  next: () => void;
  previous: () => void;
}) {
  const [isOpen, setIsOpen] = useState(false);

  const { makingStep } = currentStep;
  const { label, value } = makingStep.data;

  const state = useContext(AddSpotContext);

  const onKeyDown = (e: EventFor<'button', 'onKeyDown'>) => {
    if (e.key === 'Enter') setIsOpen(true);
    else if (e.key === 'Escape') setIsOpen(false);
  };

  function closeModal() {
    setIsOpen(false);
  }

  function handleConfirm() {
    console.log(state);
    console.log('Confirm!');

    setIsOpen(false);
  }

  if (label === '완료') {
    return (
      <nav className="mx-2 py-4 flex justify-center items-center relative">
        <span className="text-xl text-semibold text-gray-900">{label}</span>
      </nav>
    );
  }

  return (
    <nav className="mx-2 py-4 flex justify-between items-center relative">
      {value === 1 ? (
        <button className="w-12 p-2">
          <XMarkIcon className="w-6 h-6 stroke-gray-500" />
        </button>
      ) : (
        <button className="w-12 p-2" onClick={previous}>
          이전
        </button>
      )}

      <span className="text-xl text-semibold text-gray-900">{label}</span>
      {label.includes('확정') ? (
        <button
          className="w-12 p-2 text-main"
          onClick={() => setIsOpen(!isOpen)}
          onKeyDown={onKeyDown}
        >
          확정
          {isOpen && (
            <Dialog
              text="장소를 확정하시겠나요?"
              handleConfirm={handleConfirm}
              closeModal={closeModal}
            />
          )}
        </button>
      ) : (
        <button className="w-12 p-2 text-gray-500" onClick={next}>
          다음
        </button>
      )}
    </nav>
  );
}
