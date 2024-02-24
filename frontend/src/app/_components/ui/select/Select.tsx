import React, { ReactElement, useEffect, useState } from 'react';
import SelectOption from './SelectOption';
import ChevronDownIcon from '/public/icons/chevron-down.svg';

export default function Select({
  list,
  selectStyle,
  optionStyle,
  selectOption,
  onChangeSelectOption,
}: {
  list: { label: string | ReactElement; value: string | number }[];
  selectStyle?: string;
  optionStyle?: string;
  selectOption: string;
  onChangeSelectOption: (option: string | number) => void;
}) {
  const [isSortOpened, setIsSortOpened] = useState(false);
  const [viewSelectOption, setViewSelectOption] = useState<string | ReactElement | undefined>('최신순');
  const closeModal = () => {
    setIsSortOpened(false);
  };

  useEffect(() => {
    const selectedLabel = list.find((item) => item.value === selectOption);
    setViewSelectOption(selectedLabel?.label);
  }, [selectOption]);

  return (
    <ul
      tabIndex={0}
      className={`${
        selectStyle && selectStyle
      } rounded-md cursor-pointer relative`}
      onClick={() => setIsSortOpened(!isSortOpened)}
      onKeyDown={(e) => e.key === 'Enter' && setIsSortOpened(!isSortOpened)}
    >
      <div className="flex items-center justify-between p-2 gap-x-4 text-main font-semibold">
        {viewSelectOption}
        <ChevronDownIcon className="w-4 h-2 stroke-gray-500" />
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
