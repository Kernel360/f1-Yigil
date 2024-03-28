'use client';

import { useCallback, useState } from 'react';
import {
  closestCenter,
  DndContext,
  DragOverlay,
  KeyboardSensor,
  PointerSensor,
  TouchSensor,
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

import type {
  DragEndEvent,
  DragStartEvent,
  UniqueIdentifier,
} from '@dnd-kit/core';

import type { TImageData } from './ImageHandler';
import { createPortal } from 'react-dom';

export default function ImagesContainer({
  images,
  setImages,
}: {
  images: TImageData[];
  setImages: (newImages: { type: 'new'; data: TImageData[] }) => void;
}) {
  const [activeId, setActiveId] = useState<string | null>(null);

  const sensor =
    /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
      navigator.userAgent,
    )
      ? TouchSensor
      : PointerSensor;
  const sensors = useSensors(
    useSensor(sensor, { activationConstraint: { distance: 5 } }),
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
        const nextImages = imageMove(images, active.id, over?.id);

        setImages({ type: 'new', data: nextImages });

        setActiveId(null);
      }
    },
    [images, setImages],
  );

  function removeImage(filename: string) {
    const nextImages = images.filter((image) => image.filename !== filename);

    setImages({ type: 'new', data: nextImages });
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
      {activeImage &&
        createPortal(
          <DragOverlay className="origin-top-left" adjustScale>
            <ImageItem image={activeImage} isDragging />
          </DragOverlay>,
          document.body,
        )}
    </DndContext>
  );
}
