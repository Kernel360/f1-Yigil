import { EventFor } from '@/types/type';
import React, { ReactElement, useEffect, useState } from 'react';
import SelectOption from './SelectOption';
import ChevronDownIcon from '/public/icons/chevron-down.svg';

export default function Select({
  list,
  selectStyle,
  optionStyle,
  selectOption,
  onChangeSelectOption,
  defaultValue,
  defaultValueColor,
}: {
  list: { label: string | ReactElement; value: string }[];
  selectStyle?: string;
  optionStyle?: string;
  selectOption: string;
  onChangeSelectOption: (option: string) => void;
  defaultValue: string | ReactElement;
  defaultValueColor?: string;
}) {
  const [isSortOpened, setIsSortOpened] = useState(false);
  const [viewSelectOption, setViewSelectOption] = useState<
    string | ReactElement | undefined
  >(defaultValue);
  const closeModal = () => {
    setIsSortOpened(false);
  };

  const keyDownHandler = (e: EventFor<'ul', 'onKeyDown'>) => {
    e.key === 'Enter' && setIsSortOpened(!isSortOpened);
    e.key === 'Escape' && setIsSortOpened(false);
  };

  useEffect(() => {
    const selectedLabel = list.find((item) => item.value === selectOption);
    setViewSelectOption(selectedLabel?.label);
  }, [selectOption, list]);

  return (
    <ul
      tabIndex={0}
      className={` ${
        selectStyle && selectStyle
      } rounded-md cursor-pointer relative`}
      onClick={() => setIsSortOpened(!isSortOpened)}
      onKeyDown={(e) => keyDownHandler(e)}
      aria-label="select"
    >
      <div
        className={`flex items-center justify-between gap-x-2 ${
          defaultValueColor ? defaultValueColor : 'text-gray-700'
        }`}
      >
        {viewSelectOption}
        <ChevronDownIcon
          className={`w-4 h-2 stroke-gray-500 ${isSortOpened && 'rotate-180'} `}
        />
      </div>
      {isSortOpened && (
        <SelectOption
          list={list}
          optionStyle={optionStyle}
          onChangeSelectOption={onChangeSelectOption}
          closeModal={closeModal}
        />
      )}
    </ul>
  );
}
