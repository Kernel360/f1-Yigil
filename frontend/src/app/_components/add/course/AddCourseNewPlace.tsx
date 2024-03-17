'use client';

import { useContext, useState } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';
import { AddTravelMapContext } from '@/context/map/AddTravelMapContext';

import { InfoTitle } from '../common';
import AddTravelSearchBar from '../AddTravelSearchBar';

import SelectSpot from './SelectSpot';
import AddTravelPlaceInfo from '../AddTravelPlaceInfo';
import AddCourseMap from './AddCourseMap';

export default function AddCourseNewPlace() {
  const [travelMapState] = useContext(AddTravelMapContext);
  const [course] = useContext(CourseContext);
  const [index, setIndex] = useState(0);

  function selectIndex(nextIndex: number) {
    setIndex(nextIndex);
  }

  const { isMapOpen } = travelMapState;
  const { spots } = course;

  return (
    <section className={`flex flex-col grow`}>
      {spots.length === 0 && (
        <>
          <div
            className={`h-80 flex flex-col justify-center duration-500 ease-in-out ${
              isMapOpen
                ? 'collapse opacity-0 max-h-0 transition-all'
                : 'visible opacity-100 max-h-full transition-all'
            }`}
          >
            <InfoTitle label="장소" additionalLabel="를 입력하세요." />
          </div>
          <AddTravelSearchBar />
        </>
      )}
      {isMapOpen ? (
        <AddCourseMap />
      ) : (
        <div className="flex flex-col">
          {spots.length !== 0 && (
            <>
              <SelectSpot
                key="images"
                index={index}
                selectIndex={selectIndex}
              />
              <AddTravelPlaceInfo
                name={spots[index].place.name}
                address={spots[index].place.address}
                mapImageUrl={spots[index].place.mapImageUrl}
              />
            </>
          )}
        </div>
      )}
    </section>
  );
}
