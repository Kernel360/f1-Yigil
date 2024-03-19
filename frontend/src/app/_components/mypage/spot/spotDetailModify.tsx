import { EventFor } from '@/types/type';
import { Dispatch, SetStateAction, useState } from 'react';
import { TModifyDetail } from './SpotDetail';

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
