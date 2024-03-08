import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction, useState } from 'react';
import Select from '../ui/select/Select';

const selectBtns = [
  { label: '전체', value: 'all' },
  { label: '공개', value: 'public' },
  { label: '비공개', value: 'private' },
];

export const sortOptions = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  {
    label: '별점순',
    value: 'rate',
  },
];

interface TMyPageSelectBtn {
  selectOption: string;
  sortOption: string;
  onClickSelectOption: (option: string) => void;
  onChangeSortOption: (option: string | number) => void;
  onChangeAllList: (
    e: EventFor<'input', 'onChange'>,
    setIsChecked: Dispatch<SetStateAction<boolean>>,
  ) => void;
}

// 똑같이 쓰이는 부분이 있다면 컴포넌트 재사용성 고려해야 함.
export default function MyPageSelectBtns({
  selectOption,
  sortOption,
  onClickSelectOption,
  onChangeSortOption,
  onChangeAllList,
}: TMyPageSelectBtn) {
  const [isChecked, setIsChecked] = useState(false);

  return (
    <>
      <ul className="flex items-center gap-x-2">
        {selectBtns.map(({ label, value }, idx) => (
          <button
            key={idx}
            className={`py-2 px-4 rounded-full ${
              selectOption === value
                ? 'bg-gray-500 text-white'
                : 'bg-gray-200 text-gray-500'
            }`}
            onClick={() => {
              onClickSelectOption(value);
              setIsChecked(false);
            }}
          >
            {label}
          </button>
        ))}
      </ul>
      <div className="flex justify-between ml-4 mt-4">
        <div className="flex items-center gap-x-4">
          <input
            type="checkbox"
            className="w-8 h-8"
            onChange={(e) => onChangeAllList(e, setIsChecked)}
            checked={isChecked}
          />
          <div className="text-gray-700">전체선택</div>
        </div>
        <Select
          list={sortOptions}
          optionStyle={'top-10 left-0 bg-white w-full mx-auto'}
          selectOption={sortOption}
          onChangeSelectOption={onChangeSortOption}
          defaultValue="최신순"
        />
      </div>
    </>
  );
}
