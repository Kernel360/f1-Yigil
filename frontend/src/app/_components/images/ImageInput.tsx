'use client';

import { useRef } from 'react';

import { blobTodataUrl } from '@/utils';

import type { TImageData } from './ImageHandler';

import ImageIcon from '/public/icons/image.svg';
import { EventFor } from '@/types/type';

export default function ImageInput({
  availableSpace,
  images,
  setImages,
  invokeError,
  order,
}: {
  availableSpace: number;
  images: TImageData[];
  setImages: (newImages: TImageData[]) => void;
  invokeError: (title: string) => void;
  order?: string;
}) {
  const fileInputRef = useRef<HTMLInputElement>(null);

  async function handleUpload(fileList: FileList) {
    // if (availableSpace === 0) {
    //   throw Error('더 이상 추가할 수 없습니다!');
    // }

    if (fileList.length > availableSpace) {
      throw Error(`${availableSpace}개 이상 추가할 수 없습니다!`);
    }

    const newImages: TImageData[] = [];

    for (let i = 0; i < fileList.length; i++) {
      const uri = await blobTodataUrl(fileList[i]);
      newImages.push({ filename: fileList[i].name, uri });
    }

    const currentImageNames = images.map((image) => image.filename);

    const deduplicated = newImages.filter(
      (image) => !currentImageNames.includes(image.filename),
    );

    const nextImages = [...images, ...deduplicated];

    setImages(nextImages);
  }

  function handleClick(event: EventFor<'input', 'onClick'>) {
    if (availableSpace === 0) {
      event.preventDefault();
      invokeError('더 이상 추가할 수 없습니다!');
    }

    setTimeout(() => invokeError(''), 1000);
  }

  return (
    <div className="aspect-square border-2 rounded-2xl border-gray-200 bg-gray-100 shrink-0">
      <label
        className="w-full h-full rounded-2xl flex justify-center items-center hover:cursor-pointer"
        tabIndex={0}
        htmlFor={order ? `add-image-${order}` : 'add-image'}
        onKeyDown={(event) => {
          if (event.key === 'Enter') {
            fileInputRef.current?.click();
          }
        }}
      >
        <ImageIcon className="w-10 h-10" />
      </label>
      <input
        className="hidden"
        ref={fileInputRef}
        key={availableSpace}
        id={order ? `add-image-${order}` : 'add-image'}
        type="file"
        accept="image/*"
        multiple
        onClick={handleClick}
        onInput={async (event) => {
          invokeError('');

          try {
            if (!event.currentTarget.files) {
              return;
            }

            await handleUpload(event.currentTarget.files);
          } catch (error) {
            if (error instanceof Error) {
              invokeError(error.message);
            }

            console.log(error);
          }
        }}
      />
    </div>
  );
}
