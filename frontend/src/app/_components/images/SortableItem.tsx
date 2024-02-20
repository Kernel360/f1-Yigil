import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';

import ImageItem from './ImageItem';

import type { TImageData } from './ImageHandler';

export default function SortableItem({
  image,
  order,
  removeImage,
}: {
  image: TImageData;
  order: number;
  removeImage?: (filename: string) => void;
}) {
  const {
    isDragging,
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
  } = useSortable({ id: image.filename });

  const transformStyle = CSS.Transform.toString(transform) ?? '';
  const transitionStyle = transition ?? '';

  const tailWindAnimationStyle = `${transformStyle} ${transitionStyle}`.trim();

  return (
    <ImageItem
      image={image}
      order={order}
      removeImage={removeImage}
      ref={setNodeRef}
      animationStyle={tailWindAnimationStyle}
      withOpacity={isDragging}
      isDragging={isDragging}
      {...attributes}
      {...listeners}
    />
  );
}
