'use client';

import { useState } from 'react';

import Progress from '../Progress';
import SelectPlaceNavigation from '../SelectPlaceNavigation';
import Navigation from '../Navigation';
import AddCourseData from './AddCourseData';

export default function AddCourseContent() {
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
        <AddCourseData />
      </div>
    </section>
  );
}
