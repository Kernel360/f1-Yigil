'use client';

import StepProvider from '@/context/travel/step/StepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import CourseProvider from '@/context/travel/course/CourseContext';

import Navigation from '../Navigation';
import Progress from '../Progress';
import AddCourseContent from './AddCourseContent';

export default function AddCourse() {
  return (
    <section className="flex flex-col grow">
      <StepProvider course>
        <Progress />
        <Navigation />
        <PlaceProvider course>
          <CourseProvider>
            <AddCourseContent />
          </CourseProvider>
        </PlaceProvider>
      </StepProvider>
    </section>
  );
}
