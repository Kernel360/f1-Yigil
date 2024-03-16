'use client';

import { useContext } from 'react';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

import Progress from '../Progress';
import Navigation from '../Navigation';
import SelectPlaceNavigation from '../SelectPlaceNavigation';
import AddCourseData from './AddCourseData';

export default function AddCourseContent() {
  const [state] = useContext(AddTravelMapContext);

  return (
    <section className="relative flex flex-col grow">
      <div className="h-16 flex flex-col justify-center">
        {!state.isMapOpen && <Progress />}
        {state.isMapOpen ? <SelectPlaceNavigation /> : <Navigation />}
      </div>
      <div className="flex flex-col grow">
        <AddCourseData />
      </div>
    </section>
  );
}
