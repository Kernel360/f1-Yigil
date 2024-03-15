import { useContext } from 'react';
import { StepContext } from '@/context/travel/step/StepContext';
import { PlaceContext } from '@/context/travel/place/PlaceContext';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

import {
  TPlaceState,
  isDefaultChoosePlace,
  isDefaultPlace,
} from '@/context/travel/place/reducer';

import XMarkIcon from '/public/icons/x-mark.svg';

export default function SelectPlaceNavigation() {
  const [addTravelMapState, dispatchAddTravel] =
    useContext(AddTravelMapContext);
  const [, dispatchStep] = useContext(StepContext);
  const [placeState, dispatchPlaceState] = useContext(PlaceContext);

  function handleConfirm() {
    dispatchPlaceState({
      type: 'SET_PLACE',
      payload: addTravelMapState.current,
    });

    dispatchAddTravel({ type: 'CLOSE_RESULT' });
    dispatchAddTravel({ type: 'CLOSE_MAP' });
    dispatchStep({ type: 'NEXT' });
  }

  return (
    <nav className="mx-4 relative flex justify-between items-center">
      <span className="absolute left-0 right-0 text-center text-xl text-semibold text-gray-900">
        장소 선택
      </span>
      <button
        className="relative"
        onClick={() => {
          dispatchAddTravel({ type: 'CLOSE_RESULT' });
          dispatchAddTravel({ type: 'CLOSE_MAP' });
        }}
      >
        <XMarkIcon className="w-6 h-6 stroke-gray-500" />
      </button>
      {!isDefaultChoosePlace(addTravelMapState.selectedPlace) && (
        <button className="relative text-lg text-main" onClick={handleConfirm}>
          완료
        </button>
      )}
    </nav>
  );
}
