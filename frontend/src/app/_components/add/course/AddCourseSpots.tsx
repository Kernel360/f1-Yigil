'use client';

import type { TSpotState } from '@/context/travel/schema';
import AddCourseSpotData from './AddCourseSpotData';

export default function AddCourseSpots({ spots }: { spots: TSpotState[] }) {
  return (
    <section className="flex flex-col">
      {spots.map((spot, index) => (
        <div
          className="flex flex-col"
          key={`${spot.place.name}-${spot.place.address}-${index}`}
        >
          <AddCourseSpotData spot={spot} index={index} />
          {index < spots.length - 1 && (
            <div className="my-4 w-1 h-8 border-2 border-gray-300 self-center" />
          )}
        </div>
      ))}
    </section>
  );
}
