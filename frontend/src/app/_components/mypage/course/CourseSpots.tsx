import React, { Dispatch, SetStateAction } from 'react';
import CourseSpotItem from './CourseSpotItem';
import { TModifyCourse } from './CourseDetail';

export default function CourseSpots({
  spots,
  isModifyMode,
  setModifyCourse,
}: {
  spots: TModifyCourse['spots'];
  isModifyMode: boolean;
  setModifyCourse: Dispatch<SetStateAction<TModifyCourse>>;
}) {
  return (
    <section className="flex flex-col">
      {spots.map((spot, idx) => (
        <div
          className="flex flex-col"
          key={`${spot.order}-${idx}-${spot.place_name}`}
        >
          <CourseSpotItem
            spots={spots}
            spot={spot}
            isModifyMode={isModifyMode}
            setModifyCourse={setModifyCourse}
          />
          {idx < spots.length - 1 && (
            <div className="my-4 w-1 h-8 border-2 border-gray-300 self-center" />
          )}
        </div>
      ))}
    </section>
  );
}
