import type { TStep } from './step/types';

export default function ProgressIndicator({ step }: { step: TStep }) {
  const { makingStep, inputStep } = step;

  const label = makingStep.data.label;
  const hideIndicator = label === '완료' || label.includes('확정');

  const totalStepCount = inputStep.kind === 'from-new' ? 4 : 3;

  return hideIndicator ? (
    <></>
  ) : (
    <div
      className="h-1 bg-black transition-all ease-in-out duration-300"
      style={{
        width: `${Math.floor((inputStep.data.value / totalStepCount) * 100)}%`,
      }}
    ></div>
  );
}
