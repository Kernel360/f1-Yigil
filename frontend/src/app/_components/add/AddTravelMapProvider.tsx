'use client';

import NaverContext from '@/context/NaverContext';
import { createContext, useState } from 'react';

import type { ReactNode } from 'react';

export const AddTravelMapContext = createContext<
  [boolean, (nextValue: boolean) => void]
>([false, () => {}]);

export default function AddTravelMapProvider({
  children,
}: {
  children: ReactNode;
}) {
  const [isAddTravelMapOpen, setIsAddTravelMapOpen] = useState(false);

  function setIsOpen(nextValue: boolean) {
    setIsAddTravelMapOpen(nextValue);
  }

  return (
    <AddTravelMapContext.Provider value={[isAddTravelMapOpen, setIsOpen]}>
      {children}
    </AddTravelMapContext.Provider>
  );
}
