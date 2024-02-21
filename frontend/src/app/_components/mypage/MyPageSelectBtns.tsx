import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction, useState } from 'react';

const selectBtns = [
  { label: '전체', value: 'all' },
  { label: '공개', value: 'public' },
  { label: '비공개', value: 'private' },
];

interface TMyPageSelectBtn {
  selectOption: string;
  onClickSelectOption: (option: string) => void;
  onChangeSortOption: (option: string) => void;
  onChangeAllList: (
    e: EventFor<'input', 'onChange'>,
    setIsChecked: Dispatch<SetStateAction<boolean>>,
  ) => void;
}

// 똑같이 쓰이는 부분이 있다면 컴포넌트 재사용성 고려해야 함.
export default function MyPageSelectBtns({
  selectOption,
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
      <div className="flex justify-between mx-5 mb-2 mt-4">
        <div className="flex items-center gap-x-4">
          <input
            type="checkbox"
            className="w-8 h-8"
            onChange={(e) => onChangeAllList(e, setIsChecked)}
            checked={isChecked}
          />
          <div>전체 선택</div>
        </div>
        <select
          name=""
          id=""
          className="p-2"
          onChange={(e) => onChangeSortOption(e.currentTarget.value)}
        >
          <option value="desc">최신순</option>
          <option value="asc">오래된순</option>
          <option value="rate">평점순</option>
        </select>
      </div>
    </>
  );
}
