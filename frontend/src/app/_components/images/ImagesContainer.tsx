'use client';

import { useCallback, useContext, useState } from 'react';
import {
  closestCenter,
  DndContext,
  DragOverlay,
  KeyboardSensor,
  PointerSensor,
  useSensor,
  useSensors,
} from '@dnd-kit/core';
import {
  arrayMove,
  rectSortingStrategy,
  sortableKeyboardCoordinates,
  SortableContext,
} from '@dnd-kit/sortable';

import ImageItem from './ImageItem';
import SortableItem from './SortableItem';

import type { Dispatch } from 'react';
import type {
  DragEndEvent,
  DragStartEvent,
  UniqueIdentifier,
} from '@dnd-kit/core';
import type { TImageData } from './ImageHandler';
import { AddSpotContext, type TAddSpotAction } from '../add/spot/SpotContext';

export default function ImagesContainer({
  dispatch,
}: {
  dispatch: Dispatch<TAddSpotAction>;
}) {
  const { images } = useContext(AddSpotContext);

  const [activeId, setActiveId] = useState<string | null>(null);
  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 0.01 } }),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    }),
  );

  const handleDragStart = useCallback((event: DragStartEvent) => {
    setActiveId(event.active.id as string);
  }, []);

  function imageMove(
    images: TImageData[],
    previousId: UniqueIdentifier,
    currentId?: UniqueIdentifier,
  ) {
    console.log({ images, previousId, currentId });

    const oldItem = images.find((image) => image.filename === previousId);
    const newItem = images.find((image) => image.filename === currentId);

    if (oldItem && newItem) {
      const oldIndex = images.indexOf(oldItem);
      const newIndex = images.indexOf(newItem);

      return arrayMove(images, oldIndex, newIndex);
    }

    return images;
  }

  const handleDragEnd = useCallback(
    (event: DragEndEvent) => {
      const { active, over } = event;

      if (active.id !== over?.id) {
        const nextImage = imageMove(images, active.id, over?.id);

        dispatch({ type: 'SET_IMAGES', payload: nextImage });

        setActiveId(null);
      }
    },
    [images],
  );

  function removeImage(filename: string) {
    const nextImages = images.filter((image) => image.filename !== filename);

    dispatch({ type: 'SET_IMAGES', payload: nextImages });
  }

  const handleDragCancel = useCallback(() => {
    setActiveId(null);
  }, []);

  const imageUIDs = images.map((image) => image.filename);
  const activeImage = images.find((image) => image.filename === activeId);

  return (
    <DndContext
      sensors={sensors}
      collisionDetection={closestCenter}
      onDragStart={handleDragStart}
      onDragEnd={handleDragEnd}
      onDragCancel={handleDragCancel}
    >
      <SortableContext items={imageUIDs} strategy={rectSortingStrategy}>
        {images.map((image, index) => (
          <SortableItem
            key={image.filename}
            image={image}
            order={index}
            removeImage={removeImage}
          />
        ))}
      </SortableContext>
      <DragOverlay className="origin-top-left" adjustScale>
        {activeImage && <ImageItem image={activeImage} isDragging />}
      </DragOverlay>
    </DndContext>
  );
}
