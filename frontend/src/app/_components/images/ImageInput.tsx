'use client';

import { useRef } from 'react';

import type { TImageData } from './ImageHandler';

import ImageIcon from '/public/icons/image.svg';

function toBase64(file: File) {
  return new Promise<string>((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = reject;

    reader.readAsDataURL(file);
  });
}

export default function ImageInput({
  availableSpace,
}: {
  availableSpace: number;
}) {
  const fileInputRef = useRef<HTMLInputElement>(null);

  async function handleUpload(fileList: FileList) {
    try {
      if (availableSpace === 0) {
        throw Error('더 이상 추가할 수 없습니다!');
      }

      if (fileList.length > availableSpace) {
        throw Error(`${availableSpace}개 이상 추가할 수 없습니다!`);
      }

      const images: TImageData[] = [];

      for (let i = 0; i < fileList.length; i++) {
        const uri = await toBase64(fileList[i]);
        images.push({ index: i, filename: fileList[i].name, uri });
      }

      console.log(images);
    } catch (error) {
      alert(error);
    }
  }

  return (
    <div className="aspect-square border-2 rounded-2xl border-gray-200 bg-gray-100 shrink-0">
      <label
        className="w-full h-full rounded-2xl flex justify-center items-center"
        tabIndex={0}
        htmlFor="add-image"
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
        id="add-image"
        type="file"
        accept="image/*"
        multiple
        onChange={(event) => {
          if (!event.currentTarget.files) {
            return;
          }

          handleUpload(event.currentTarget.files);
        }}
      />
    </div>
  );
}
