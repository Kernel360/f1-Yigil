'use client';

import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';

import SortableSpotItem from './SortableSpotItem';

import type { TSpotState } from '@/context/travel/schema';

export default function SortableSpot({
  spot,
  index,
}: {
  spot: TSpotState;
  index: number;
}) {
  const spotId = spot.id ? spot.id.toString() : '-1';

  const {
    isDragging,
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
  } = useSortable({ id: spotId });

  const transformStyle = CSS.Transform.toString(transform) ?? '';
  const transitionStyle = transition ?? '';

  const tailWindAnimationStyle = `${transformStyle} ${transitionStyle}`.trim();

  return (
    <SortableSpotItem
      spot={spot}
      index={index}
      ref={setNodeRef}
      animationStyle={tailWindAnimationStyle}
      withOpacity={isDragging}
      isDragging={isDragging}
      {...attributes}
      {...listeners}
    />
  );
}
