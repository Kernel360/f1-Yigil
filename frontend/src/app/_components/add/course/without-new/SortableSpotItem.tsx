'use client';

import { forwardRef, type HTMLAttributes } from 'react';
import type { TSpotState } from '@/context/travel/schema';
import AddCourseSpotData from '../AddCourseSpotData';

type ItemProps = HTMLAttributes<HTMLDivElement> & {
  spot: TSpotState;
  index?: number;
  animationStyle?: string;
  withOpacity?: boolean;
  isDragging?: boolean;
};

const SortableSpotItem = forwardRef<HTMLDivElement, ItemProps>(
  ({ spot, index, animationStyle, withOpacity, isDragging, ...props }, ref) => {
    return (
      <div
        ref={ref}
        className={`${withOpacity ? 'opacity-50' : 'opacity-100'} ${
          isDragging ? 'cursor-grabbing scale-105' : 'scale-100'
        } ${animationStyle}`}
        {...props}
      >
        <AddCourseSpotData spot={spot} index={index} />
      </div>
    );
  },
);
SortableSpotItem.displayName = 'SortableSpotItem';

export default SortableSpotItem;
