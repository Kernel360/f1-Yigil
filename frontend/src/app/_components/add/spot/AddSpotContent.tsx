'use client';

import { useState } from 'react';

import Progress from '../Progress';
import Navigation from '../Navigation';
import SelectPlaceNavigation from '../SelectPlaceNavigation';
import AddSpotData from './AddSpotData';

export default function AddSpotContent() {
  const [isSelecting, setIsSelecting] = useState(false);

  function startSelect() {
    setIsSelecting(true);
  }

  function endSelect() {
    setIsSelecting(false);
  }

  return (
    <section className="relative flex flex-col grow">
      <div className="h-16 flex flex-col justify-center">
        {!isSelecting && <Progress />}
        {isSelecting ? (
          <SelectPlaceNavigation endSelect={endSelect} />
        ) : (
          <Navigation />
        )}
      </div>
      <div className="flex flex-col grow">
        <AddSpotData />
      </div>
    </section>
  );
}
