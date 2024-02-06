import { forwardRef } from 'react';
import Image from 'next/image';

import type { HTMLAttributes } from 'react';
import type { TImageData } from './ImageHandler';

type ItemProps = HTMLAttributes<HTMLDivElement> & {
  image: TImageData;
  order?: number;
  animationStyle?: string;
  withOpacity?: boolean;
  isDragging?: boolean;
};

const ImageItem = forwardRef<HTMLDivElement, ItemProps>(
  (
    { image, order, animationStyle, withOpacity, isDragging, ...props },
    ref,
  ) => {
    console.log(order);

    return (
      <div
        ref={ref}
        className={`relative aspect-square border-2 rounded-2xl origin-center overflow-hidden ${
          withOpacity ? 'opacity-50' : 'opacity-100'
        } ${
          isDragging ? 'cursor-grabbing scale-105' : 'scale-100'
        } ${animationStyle}`}
        {...props}
      >
        <Image src={image.uri} alt="Uploaded image" fill />
        {order === 0 && (
          <span className="absolute top-2 right-2 px-[8px] py-[2px] rounded-2xl border-2 border-main bg-white text-sm text-main">
            대표
          </span>
        )}
      </div>
    );
  },
);

export default ImageItem;
