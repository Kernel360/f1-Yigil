'use client';

import NaverContext from '@/context/NaverContext';
import CourseWithNewStepProvider from '@/context/travel/step/course/CourseWithNewStepContext';
import CourseWithoutNewStepProvider from '@/context/travel/step/course/CourseWithoutNewStepContext';
import CourseProvider from '@/context/travel/course/CourseContext';
import AddTravelMapProvider from '@/context/map/AddTravelMapContext';
import SearchProvider from '@/context/search/SearchContext';
import AddCourseContent from './AddCourseContent';
import AddTravelExistsProvider from '@/context/exists/AddTravelExistsContext';

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
        <CourseProvider>
          <CourseWithNewStepProvider>
            <CourseWithoutNewStepProvider>
              <AddTravelMapProvider>
                <AddTravelExistsProvider>
                  <SearchProvider initialKeyword={initialKeyword}>
                    <AddCourseContent />
                  </SearchProvider>
                </AddTravelExistsProvider>
              </AddTravelMapProvider>
            </CourseWithoutNewStepProvider>
          </CourseWithNewStepProvider>
        </CourseProvider>
      </NaverContext>
    </section>
  );
}
