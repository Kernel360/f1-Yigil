'use client';

import { useContext, useState } from 'react';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { CourseWithNewStepContext } from '@/context/travel/step/course/CourseWithNewStepContext';
import { CourseWithoutNewStepContext } from '@/context/travel/step/course/CourseWithoutNewStepContext';

import CourseProgress from './CourseProgress';
import CourseNavigation from './CourseNavigation';
import AddCourseData from './AddCourseData';
import AddCourseSelectPlaceNavigation from './AddCourseSelectPlaceNavigation';

export default function AddCourseContent() {
  const [state, dispatchAddTravelMap] = useContext(AddTravelMapContext);
  const [withNew] = useContext(CourseWithNewStepContext);
  const [withoutNew] = useContext(CourseWithoutNewStepContext);

  const [method, setMethod] = useState<'with-new' | 'without-new'>('with-new');

  function onSelectMethod(nextMethod: 'with-new' | 'without-new') {
    setMethod(nextMethod);
  }

  return (
    <section className="relative flex flex-col grow">
      <div
        className="h-16 flex flex-col justify-center"
        onClick={() => dispatchAddTravelMap({ type: 'CLOSE_RESULT' })}
      >
        {state.isMapOpen ? (
          <AddCourseSelectPlaceNavigation />
        ) : (
          <CourseNavigation method={method} />
        )}
      </div>
      <div className="flex flex-col grow">
        {!state.isMapOpen && (
          <CourseProgress
            currentValue={
              method === 'with-new' ? withNew.value : withoutNew.value
            }
          />
        )}
        <AddCourseData method={method} onSelect={onSelectMethod} />
      </div>
    </section>
  );
}
