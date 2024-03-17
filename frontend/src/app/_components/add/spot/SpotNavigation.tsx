'use client';

import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import { SpotStepContext } from '@/context/travel/step/spot/SpotStepContext';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { isDefaultPlace } from '@/context/travel/place/reducer';

import ToastMsg from '@/app/_components/ui/toast/ToastMsg';

import type { TSpotState } from '@/context/travel/schema';
import type { TSpotStepState } from '@/context/travel/step/spot/reducer';

function canGoNext(spot: TSpotState, step: TSpotStepState): true | string {
  switch (step.value) {
    case 1: {
      if (isDefaultPlace({ type: 'spot', data: spot.place })) {
        return '장소를 선택해주세요!';
      }

      return true;
    }
    case 2:
      return true;
    case 3:
      return true;
    case 4:
      return true;
  }
}

export default function SpotNavigation() {
  const [step, dispatch] = useContext(SpotStepContext);
  const [spot, dispatchSpot] = useContext(SpotContext);
  const [error, setError] = useState('');

  const { back } = useRouter();

  return (
    <nav className="mx-4 flex justify-between items-center">
      <button
        onClick={() => {
          // Spot 추가 / 장소 입력 단계일 때
          if (step.value === 1) {
            if (!isDefaultPlace({ type: 'spot', data: spot.place })) {
              dispatchSpot({ type: 'INIT_SPOT' });
            } else {
              back();
            }

            return;
          }

          dispatch({ type: 'PREVIOUS' });
        }}
      >
        이전
      </button>
      <span className="text-xl text-semibold text-gray-900">{step.label}</span>
      {step.value === 4 ? (
        <button className="text-blue-500">확정</button>
      ) : (
        <button
          onClick={() => {
            const result = canGoNext(spot, step);

            if (result !== true) {
              setError(result);
              return;
            }

            dispatch({ type: 'NEXT' });
          }}
        >
          다음
        </button>
      )}
      <div className="absolute">
        {error && <ToastMsg title={error} id={Date.now()} timer={2000} />}
      </div>
    </nav>
  );
}
