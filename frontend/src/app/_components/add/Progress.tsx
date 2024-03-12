import { useContext } from 'react';
import { StepContext } from '@/context/travel/step/StepContext';

export default function Progress() {
  const [step] = useContext(StepContext);

  return (
    <div
      className="absolute top-0 h-1 bg-black transition-all ease-in-out duration-300"
      style={{
        width: `${Math.floor((step.data.value / 4) * 100)}%`,
      }}
    ></div>
  );
}
