import { useContext } from 'react';
import { StepContext } from '@/context/travel/step/StepContext';
import { PlaceContext } from '@/context/travel/place/PlaceContext';

import { isDefaultPlace } from '@/context/travel/place/reducer';

import XMarkIcon from '/public/icons/x-mark.svg';

export default function SelectPlaceNavigation({
  endSelect,
}: {
  endSelect: () => void;
}) {
  const [, dispatchStep] = useContext(StepContext);
  const [placeState] = useContext(PlaceContext);

  function handleConfirm() {
    if (isDefaultPlace(placeState)) {
      console.error('장소를 선택해주세요!');
    }

    endSelect();
    dispatchStep({ type: 'NEXT' });
  }

  return (
    <nav className="mx-4 relative flex justify-between items-center">
      <span className="absolute left-0 right-0 text-center text-xl text-semibold text-gray-900">
        장소 선택
      </span>
      <button className="relative" onClick={endSelect}>
        <XMarkIcon className="w-6 h-6 stroke-gray-500" />
      </button>
      {!isDefaultPlace(placeState) && (
        <button className="relative text-lg text-main" onClick={handleConfirm}>
          완료
        </button>
      )}
    </nav>
  );
}
