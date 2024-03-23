'use client';

import { useContext, useState } from 'react';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';
import { CourseWithNewStepContext } from '@/context/travel/step/course/CourseWithNewStepContext';
import { CourseWithoutNewStepContext } from '@/context/travel/step/course/CourseWithoutNewStepContext';

import CourseProgress from './CourseProgress';
import CourseNavigation from './CourseNavigation';
import AddCourseData from './AddCourseData';
import AddCourseSelectPlaceNavigation from './AddCourseSelectPlaceNavigation';
import ToastMsg from '../../ui/toast/ToastMsg';

export default function AddCourseContent() {
  const [state, dispatchAddTravelMap] = useContext(AddTravelMapContext);
  const [withNew, dispatchWithNew] = useContext(CourseWithNewStepContext);
  const [withoutNew, dispatchWithoutNew] = useContext(
    CourseWithoutNewStepContext,
  );

  const [error, setError] = useState('');

  const [method, setMethod] = useState<'with-new' | 'without-new'>('with-new');

  function onSelectMethod(nextMethod: 'with-new' | 'without-new') {
    if (nextMethod === 'without-new') {
      setError('준비 중입니다!');
      setTimeout(() => setError(''), 2000);
      return;
    }

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
          <CourseNavigation
            step={method === 'with-new' ? withNew : withoutNew}
            dispatchStep={
              method === 'with-new' ? dispatchWithNew : dispatchWithoutNew
            }
          />
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
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </section>
  );
}
