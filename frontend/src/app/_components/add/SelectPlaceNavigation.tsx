import { useContext } from 'react';

import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { SpotContext } from '@/context/travel/spot/SpotContext';

import { isDefaultChoosePlace } from '@/context/travel/place/reducer';

import XMarkIcon from '/public/icons/x-mark.svg';

export default function SelectPlaceNavigation() {
  const [addTravelMapState, dispatchAddTravel] =
    useContext(AddTravelMapContext);
  const [, dispatchSpot] = useContext(SpotContext);

  function handleConfirm() {
    if (addTravelMapState.current.type === 'spot') {
      dispatchSpot({
        type: 'SET_PLACE',
        payload: addTravelMapState.current.data,
      });
    }

    dispatchAddTravel({ type: 'CLOSE_RESULT' });
    dispatchAddTravel({ type: 'CLOSE_MAP' });
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
