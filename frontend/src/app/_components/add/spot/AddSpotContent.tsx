'use client';

import { useContext, useState } from 'react';
import { AddTravelMapContext } from '../AddTravelMapProvider';

import Progress from '../Progress';
import Navigation from '../Navigation';
import SelectPlaceNavigation from '../SelectPlaceNavigation';
import AddSpotData from './AddSpotData';

export default function AddSpotContent() {
  const [isMapOpen] = useContext(AddTravelMapContext);

  return (
    <section className="relative flex flex-col grow">
      <div className="h-16 flex flex-col justify-center">
        {!isMapOpen && <Progress />}
        {isMapOpen ? <SelectPlaceNavigation /> : <Navigation />}
      </div>
      <div className="flex flex-col grow">
        <AddSpotData />
      </div>
    </section>
  );
}
