'use client';

import { useContext } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

import BaseSearchItem from '../search/BaseSearchItem';

import { getCoords } from '../search/action';

export default function AddTravelSearchItem({
  place,
  setError,
}: {
  place: { name: string; roadAddress: string };
  setError: (error: string) => void;
}) {
  const [, dispatch] = useContext(SearchContext);
  const [, dispatchAddTravelMap] = useContext(AddTravelMapContext);

  async function onSelect() {
    dispatch({ type: 'SET_LOADING', payload: true });

    try {
      const result = await getCoords(place.roadAddress);

      if (result.status === 'failed') {
        throw new Error('위치를 얻어올 수 없습니다!');
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
      if (error instanceof Error) {
        setError(error.message);
      }
    } finally {
      dispatch({ type: 'SET_LOADING', payload: false });
    }
  }

  return (
    <BaseSearchItem
      label={place.name.replace('&amp;', '&')}
      onSelect={onSelect}
    />
  );
}
