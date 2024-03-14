'use client';

import { useContext } from 'react';
import { AddTravelMapContext } from './AddTravelMapProvider';

import BaseSearchBar from '../search/BaseSearchBar';

import type { EventFor } from '@/types/type';

export default function AddTravelSearchBar() {
  const [, setIsOpen] = useContext(AddTravelMapContext);

  function onFocus(event: EventFor<'input', 'onFocus'>) {
    setIsOpen(true);
  }

  return <BaseSearchBar placeholder="장소를 입력하세요." onFocus={onFocus} />;
}
