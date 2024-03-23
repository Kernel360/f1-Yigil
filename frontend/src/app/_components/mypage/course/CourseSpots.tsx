import React, { Dispatch, SetStateAction } from 'react';
import { TModifyCourse } from './CourseDetail';
import CourseSpotContainer from './CourseSpotContainer';
import { EventFor } from '@/types/type';

export default function CourseSpots({
  spots,
  isModifyMode,
  setModifyCourse,
  changedSpotIdOrder,
  onClickDeleteSpot,
}: {
  spots: TModifyCourse['spots'];
  isModifyMode: boolean;
  setModifyCourse: Dispatch<SetStateAction<TModifyCourse>>;
  changedSpotIdOrder: (idOrder: number[]) => void;
  onClickDeleteSpot: (e: EventFor<'span', 'onClick'>, id: number) => void;
}) {
  return (
    <CourseSpotContainer
      spots={spots}
      setModifyCourse={setModifyCourse}
      isModifyMode={isModifyMode}
      changedSpotIdOrder={changedSpotIdOrder}
      onClickDeleteSpot={onClickDeleteSpot}
    />
  );
}
