'use client';

import { useCallback, useContext, useEffect } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

import BaseSearchItem from '../search/BaseSearchItem';

import { getCoords } from '../search/action';

export default function AddTravelSearchItem({
  place,
}: {
  place: { name: string; roadAddress: string };
}) {
  const [, dispatch] = useContext(SearchContext);
  const [, dispatchAddTravelMap] = useContext(AddTravelMapContext);

  async function onSelect() {
    dispatch({ type: 'SET_LOADING', payload: true });

    try {
      const result = await getCoords(place.roadAddress);

      if (result.status === 'failed') {
        throw new Error(result.message);
      }

      const { name, roadAddress } = place;

      dispatchAddTravelMap({
        type: 'SET_CURRENT_PLACE',
        payload: {
          type: 'spot',
          data: {
            name,
            address: roadAddress,
            coords: result.content,
            mapImageUrl: '',
          },
        },
      });
      dispatchAddTravelMap({ type: 'CLOSE_RESULT' });
    } catch (error) {
      console.error(error);
    } finally {
      dispatch({ type: 'SET_LOADING', payload: false });
    }
  }

  return <BaseSearchItem label={place.name} onSelect={onSelect} />;
}
