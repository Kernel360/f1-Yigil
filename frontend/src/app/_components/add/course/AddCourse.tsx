'use client';

import NaverContext from '@/context/NaverContext';
import StepProvider from '@/context/travel/step/StepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import CourseProvider from '@/context/travel/course/CourseContext';
import AddTravelMapProvider from '@/context/map/AddTravelMapContext';
import SearchProvider from '@/context/search/SearchContext';
import AddCourseContent from './AddCourseContent';

export default function AddCourse({ ncpClientId }: { ncpClientId: string }) {
  return (
    <section className="h-full flex flex-col grow">
      <NaverContext ncpClientId={ncpClientId}>
        <PlaceProvider course>
          <CourseProvider>
            <StepProvider course>
              <AddTravelMapProvider>
                <SearchProvider>
                  <AddCourseContent />
                </SearchProvider>
              </AddTravelMapProvider>
            </StepProvider>
          </CourseProvider>
        </PlaceProvider>
      </NaverContext>
    </section>
  );
}
