'use client';

import NaverContext from '@/context/NaverContext';
import CourseWithNewStepProvider from '@/context/travel/step/course/CourseWithNewStepContext';
import CourseWithoutNewStepProvider from '@/context/travel/step/course/CourseWithoutNewStepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import CourseProvider from '@/context/travel/course/CourseContext';
import AddTravelMapProvider from '@/context/map/AddTravelMapContext';
import SearchProvider from '@/context/search/SearchContext';
import AddCourseContent from './AddCourseContent';

export default function AddCourse({
  ncpClientId,
  initialKeyword,
}: {
  ncpClientId: string;
  initialKeyword: string;
}) {
  return (
    <section className="h-full flex flex-col grow">
      <NaverContext ncpClientId={ncpClientId}>
        <PlaceProvider course>
          <CourseProvider>
            <CourseWithNewStepProvider>
              <CourseWithoutNewStepProvider>
                <AddTravelMapProvider>
                  <SearchProvider initialKeyword={initialKeyword}>
                    <AddCourseContent />
                  </SearchProvider>
                </AddTravelMapProvider>
              </CourseWithoutNewStepProvider>
            </CourseWithNewStepProvider>
          </CourseProvider>
        </PlaceProvider>
      </NaverContext>
    </section>
  );
}
