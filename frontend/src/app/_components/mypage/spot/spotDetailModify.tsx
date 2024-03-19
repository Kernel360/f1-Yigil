import { EventFor } from '@/types/type';
import { Dispatch, SetStateAction, useState } from 'react';
import { TModifyDetail } from './SpotDetail';
import IconWithCounts from '../../IconWithCounts';
import StarIcon from '/public/icons/star.svg';
import Select from '../../ui/select/Select';
import ImageHandler from '../../images';
import { TImageData } from '../../images/ImageHandler';

export const TextArea = ({
  description,
  setModifyDetail,
  isValidated,
  setIsValidated,
}: {
  description: string;
  setModifyDetail: Dispatch<SetStateAction<TModifyDetail>>;
  isValidated: boolean;
  setIsValidated: Dispatch<SetStateAction<boolean>>;
}) => {
  const onChange = (e: EventFor<'textarea', 'onChange'>) => {
    setModifyDetail((prev) => ({
      ...prev,
      description: e.target.value,
    }));
    if (e.target.value.length > 30 || !e.target.value.length)
      setIsValidated(false);
    else {
      setIsValidated(true);
    }
  };
  return (
    <>
      <span
        className={`text-end ${
          isValidated ? 'text-gray-500' : 'text-red-500'
        } my-[-10px]`}
      >
        {description.length} / 30
      </span>
      <textarea
        placeholder={description}
        className={`p-4 h-1/5 border-2 ${
          isValidated ? 'border-violet' : 'border-red-500'
        } bg-gray-100 rounded-xl text-lg resize-none`}
        onChange={onChange}
        value={description}
      />
    </>
  );
};

const starList = Array.from({ length: 5 }, (v, idx) => ({
  label: (
    <span className="text-gray-500 text-xl leading-6">
      <IconWithCounts
        icon={<StarIcon className="w-6 h-6 fill-[#FAbb15] pb-[3px]" />}
        count={idx + 1}
        rating
      />
    </span>
  ),
  value: `${idx + 1}.0`,
}));

export const SelectContainer = ({
  selectOption,
  rate,
  onChangeSelectOption,
}: {
  selectOption: string;
  rate: string;
  onChangeSelectOption: (option: string | number) => void;
}) => {
  return (
    <span className="border-violet border-2 rounded-md">
      <Select
        list={starList}
        selectOption={selectOption}
        defaultValue={rate}
        onChangeSelectOption={onChangeSelectOption}
      />
    </span>
  );
};

export const ModifyImage = ({
  image_urls,
  setImages,
}: {
  image_urls: TImageData[];
  setImages: (newImages: TImageData[]) => void;
}) => {
  return (
    <div className="h-1/3">
      <ImageHandler images={image_urls} setImages={setImages} />
    </div>
  );
};
