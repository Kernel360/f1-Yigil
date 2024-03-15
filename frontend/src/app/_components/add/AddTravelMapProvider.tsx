'use client';

import { createContext, useState } from 'react';

import type { ReactNode } from 'react';

interface TCurrentPlace {
  name: string;
  roadAddress: string;
  coords: { lng: number; lat: number };
}

const initialCurrentPlace: TCurrentPlace = {
  name: '',
  roadAddress: '',
  coords: { lng: 0, lat: 0 },
};

export const AddTravelMapContext = createContext<
  [
    [boolean, (nextValue: boolean) => void],
    [TCurrentPlace, (nextValue: TCurrentPlace) => void],
  ]
>([
  [false, () => {}],
  [initialCurrentPlace, () => {}],
]);

export default function AddTravelMapProvider({
  children,
}: {
  children: ReactNode;
}) {
  const [isAddTravelMapOpen, setIsAddTravelMapOpen] = useState(false);
  const [selected, setSelected] = useState<TCurrentPlace>(initialCurrentPlace);

  function setIsOpen(nextValue: boolean) {
    setIsAddTravelMapOpen(nextValue);
  }

  function setSelectedPlace(nextPlace: TCurrentPlace) {
    setSelected(nextPlace);
  }

  return (
    <AddTravelMapContext.Provider
      value={[
        [isAddTravelMapOpen, setIsOpen],
        [selected, setSelectedPlace],
      ]}
    >
      {children}
    </AddTravelMapContext.Provider>
  );
}
