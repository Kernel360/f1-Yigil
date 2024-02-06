import type { TStep } from './types';

import XMarkIcon from '/public/icons/x-mark.svg';

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
  const { makingStep } = currentStep;

  return (
    <nav className="mx-2 py-4 flex justify-between items-center relative">
      {makingStep.data.value === 1 ? (
        <button className="w-12 p-2">
          <XMarkIcon className="w-6 h-6 stroke-gray-500" />
        </button>
      ) : (
        <button className="w-12 p-2" onClick={previous}>
          이전
        </button>
      )}

      <span className="text-xl text-semibold text-gray-900">
        {makingStep.data.label}
      </span>
      {makingStep.data.label === '완료' ? (
        <button className="w-12 p-2 text-main">확정</button>
      ) : (
        <button className="w-12 p-2 text-gray-500" onClick={next}>
          다음
        </button>
      )}
    </nav>
  );
}
