'use client';

import { useContext } from 'react';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

import SpotProgress from './SpotProgress';
import SpotNavigation from './SpotNavigation';
import AddSpotSelectPlaceNavigation from './AddSpotSelectPlaceNavigation';
import AddSpotData from './AddSpotData';

export default function AddSpotContent() {
  const [state] = useContext(AddTravelMapContext);

  return (
    <section className="relative flex flex-col grow">
      <div className="h-16 flex flex-col justify-center">
        {state.isMapOpen ? (
          <AddSpotSelectPlaceNavigation />
        ) : (
          <SpotNavigation />
        )}
      </div>
      <div className="flex flex-col grow">
        {!state.isMapOpen && <SpotProgress />}
        <AddSpotData />
      </div>
    </section>
  );
}
