import { useCallback, useContext, useState } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

import {
  DndContext,
  DragOverlay,
  KeyboardSensor,
  PointerSensor,
  closestCenter,
  useSensor,
  useSensors,
} from '@dnd-kit/core';
import {
  SortableContext,
  arrayMove,
  rectSortingStrategy,
  sortableKeyboardCoordinates,
} from '@dnd-kit/sortable';

import { InfoTitle } from '../../common';
import SortableSpotItem from './SortableSpotItem';

import type {
  DragEndEvent,
  DragStartEvent,
  UniqueIdentifier,
} from '@dnd-kit/core';
import type { TSpotState } from '@/context/travel/schema';
import SortableSpot from './SortableSpot';

export default function AddCourseOrder() {
  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 0.01 } }),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    }),
  );

  const [activeId, setActiveId] = useState<string | null>(null);

  const handleDragStart = useCallback((event: DragStartEvent) => {
    setActiveId(event.active.id as string);
  }, []);

  const [state, dispatch] = useContext(CourseContext);

  function spotMove(
    spots: TSpotState[],
    previousId: UniqueIdentifier,
    currentId?: UniqueIdentifier,
  ) {
    const oldSpot = spots.find((spot) => spot.id?.toString() === previousId);
    const newSpot = spots.find((spot) => spot.id?.toString() === currentId);

    if (oldSpot && newSpot) {
      const oldIndex = spots.indexOf(oldSpot);
      const newIndex = spots.indexOf(newSpot);

      return arrayMove(spots, oldIndex, newIndex);
    }
  }

  const handleDragEnd = useCallback(
    (event: DragEndEvent) => {
      const { active, over } = event;

      if (active.id !== over?.id) {
        const nextSpots = spotMove(state.spots, active.id, over?.id);

        dispatch({ type: 'CHANGE_SPOT_ORDER', payload: nextSpots });

        setActiveId(null);
      }
    },
    [state.spots, dispatch],
  );

  const handleDragCancel = useCallback(() => {
    setActiveId(null);
  }, []);

  const spotsId = state.spots.map((spot) => (spot.id ? spot.id : -1));
  const activeSpot = state.spots.find(
    (spot) => spot.id?.toString() === activeId,
  );

  return (
    <section className="flex flex-col grow">
      <div className="flex flex-col min-h-64 justify-center">
        <InfoTitle label="순서" additionalLabel="를 정하세요." />
      </div>
      <DndContext
        sensors={sensors}
        collisionDetection={closestCenter}
        onDragStart={handleDragStart}
        onDragEnd={handleDragEnd}
        onDragCancel={handleDragCancel}
      >
        <SortableContext items={spotsId} strategy={rectSortingStrategy}>
          {state.spots.map((spot, index) => (
            <div key={spot.id}>
              <SortableSpot spot={spot} index={index} />
              {index < state.spots.length - 1 && (
                <div className="flex justify-center">
                  <div className="my-4 w-1 h-8 border-2 border-gray-300" />
                </div>
              )}
            </div>
          ))}
        </SortableContext>
        <DragOverlay className="origin-top-left" adjustScale>
          {activeSpot && (
            <SortableSpotItem
              spot={activeSpot}
              index={state.spots.indexOf(activeSpot)}
              isDragging
            />
          )}
        </DragOverlay>
      </DndContext>
    </section>
  );
}
