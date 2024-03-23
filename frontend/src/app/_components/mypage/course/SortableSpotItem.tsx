import { useSortable } from '@dnd-kit/sortable';
import React, { Dispatch, SetStateAction } from 'react';
import { TModifyCourse } from './CourseDetail';
import { CSS } from '@dnd-kit/utilities';
import CourseSpotItem from './CourseSpotItem';
import { EventFor } from '@/types/type';

export default function SortableSpotItem({
  spot,
  spots,
  isModifyMode,
  setModifyCourse,
  idx,
  changedSpotIdOrder,
  onClickDeleteSpot,
}: {
  spot: TModifyCourse['spots'][0];
  spots: TModifyCourse['spots'];
  isModifyMode: boolean;
  setModifyCourse: Dispatch<SetStateAction<TModifyCourse>>;
  idx: number;
  changedSpotIdOrder: (idOrder: number[]) => void;
  onClickDeleteSpot: (e: EventFor<'span', 'onClick'>, id: number) => void;
}) {
  const {
    isDragging,
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
  } = useSortable({ id: spot.id });

  const transformStyle = CSS.Transform.toString(transform) ?? '';
  const transitionStyle = transition ?? '';

  const tailWindAnimationStyle = `${transformStyle} ${transitionStyle}`.trim();

  return (
    <CourseSpotItem
      spot={spot}
      spots={spots}
      idx={idx}
      isModifyMode={isModifyMode}
      setModifyCourse={setModifyCourse}
      ref={setNodeRef}
      animationStyle={tailWindAnimationStyle}
      withOpacity={isDragging}
      isDragging={isDragging}
      changedSpotIdOrder={changedSpotIdOrder}
      onClickDeleteSpot={onClickDeleteSpot}
      {...attributes}
      {...listeners}
    />
  );
}
