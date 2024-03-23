import React, { Dispatch, SetStateAction, useCallback, useState } from 'react';
import CourseSpotItem from './CourseSpotItem';
import { TModifyCourse } from './CourseDetail';
import {
  closestCenter,
  closestCorners,
  DndContext,
  DragEndEvent,
  DragOverlay,
  DragStartEvent,
  KeyboardSensor,
  PointerSensor,
  UniqueIdentifier,
  useSensor,
  useSensors,
} from '@dnd-kit/core';
import {
  arrayMove,
  sortableKeyboardCoordinates,
  SortableContext,
  verticalListSortingStrategy,
  rectSortingStrategy,
} from '@dnd-kit/sortable';
import SortableSpotItem from './SortableSpotItem';
import { EventFor } from '@/types/type';
import { createPortal } from 'react-dom';
import { restrictToVerticalAxis } from '@dnd-kit/modifiers';

export default function CourseSpotContainer({
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
  const [activeId, setActiveId] = useState<number | null>(null);
  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 5 } }),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    }),
  );

  const handleDragStart = useCallback((event: DragStartEvent) => {
    setActiveId(event.active.id as number);
  }, []);

  const handleDragEnd = useCallback(
    (event: DragEndEvent) => {
      const { active, over } = event;

      if (active.id !== over?.id) {
        const nextSpot = courseMove(spots, active.id, over?.id);
        const spotIdOrder = nextSpot.map((spot) => spot.id);
        setModifyCourse((prev) => ({ ...prev, spots: nextSpot }));
        changedSpotIdOrder(spotIdOrder);
        setActiveId(null);
      }
    },
    [spots, setModifyCourse, changedSpotIdOrder],
  );

  const handleDragCancel = useCallback(() => {
    setActiveId(null);
  }, []);

  const courseMove = (
    spots: TModifyCourse['spots'],
    previousId: UniqueIdentifier,
    currentId?: UniqueIdentifier,
  ) => {
    const oldItem = spots.find((spot) => spot.id === previousId);
    const newItem = spots.find((spot) => spot.id === currentId);
    if (oldItem && newItem) {
      const oldIndex = spots.indexOf(oldItem);
      const newIndex = spots.indexOf(newItem);

      return arrayMove(spots, oldIndex, newIndex);
    }
    return spots;
  };

  const courseUIDs = spots.map((spot) => spot.id);
  const activeSpot = spots.find((spot) => spot.id === activeId);
  return !isModifyMode ? (
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
            idx={idx}
            changedSpotIdOrder={changedSpotIdOrder}
            onClickDeleteSpot={onClickDeleteSpot}
          />
          {idx < spots.length - 1 && (
            <div className="my-4 w-1 h-8 border-2 border-gray-300 self-center" />
          )}
        </div>
      ))}
    </section>
  ) : (
    <DndContext
      id="spot-context"
      sensors={sensors}
      collisionDetection={closestCenter}
      onDragStart={handleDragStart}
      onDragEnd={handleDragEnd}
      onDragCancel={handleDragCancel}
      modifiers={[restrictToVerticalAxis]}
    >
      <SortableContext
        items={courseUIDs}
        strategy={verticalListSortingStrategy}
      >
        <section className="flex flex-col">
          {spots.map((spot, idx) => (
            <div
              className="flex flex-col"
              key={`${spot.order}-${idx}-${spot.place_name}`}
            >
              <SortableSpotItem
                spot={spot}
                spots={spots}
                isModifyMode={isModifyMode}
                setModifyCourse={setModifyCourse}
                idx={idx}
                changedSpotIdOrder={changedSpotIdOrder}
                onClickDeleteSpot={onClickDeleteSpot}
              />
              {idx < spots.length - 1 && (
                <div className="my-4 w-1 h-8 border-2 border-gray-300 self-center" />
              )}
            </div>
          ))}
        </section>
      </SortableContext>
      <DragOverlay className="origin-top-left" adjustScale>
        {activeSpot && (
          <CourseSpotItem
            spot={activeSpot}
            spots={spots}
            isModifyMode={isModifyMode}
            setModifyCourse={setModifyCourse}
            idx={Number(activeSpot.order)}
            changedSpotIdOrder={changedSpotIdOrder}
            onClickDeleteSpot={onClickDeleteSpot}
            isDragging
          />
        )}
      </DragOverlay>
    </DndContext>
  );
}
