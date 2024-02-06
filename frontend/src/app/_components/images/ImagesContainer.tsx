'use client';

import { useCallback, useState } from 'react';
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

import type { Dispatch, SetStateAction } from 'react';
import type { DragEndEvent, DragStartEvent } from '@dnd-kit/core';
import type { TImageData } from './ImageHandler';

export default function ImagesContainer({
  images,
  setImages,
}: {
  images: TImageData[];
  setImages: Dispatch<SetStateAction<TImageData[]>>;
}) {
  const [activeId, setActiveId] = useState<string | null>(null);
  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 0.01 } }),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    }),
  );

  const handleDragStart = useCallback((event: DragStartEvent) => {
    console.log('start');
    setActiveId(event.active.id as string);
  }, []);

  const handleDragEnd = useCallback((event: DragEndEvent) => {
    console.log('end');
    const { active, over } = event;

    if (active.id !== over?.id) {
      setImages((images) => {
        const oldItem = images.find((image) => image.filename === active.id);
        const newItem = images.find((image) => image.filename === over?.id);

        if (oldItem && newItem) {
          const oldIndex = images.indexOf(oldItem);
          const newIndex = images.indexOf(newItem);

          return arrayMove(images, oldIndex, newIndex);
        }

        return images;
      });

      setActiveId(null);
    }
  }, []);

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
          <SortableItem order={index} key={image.filename} image={image} />
        ))}
      </SortableContext>
      <DragOverlay className="origin-top-left" adjustScale>
        {activeImage && <ImageItem image={activeImage} isDragging />}
      </DragOverlay>
    </DndContext>
  );
}
