'use client';

import { useContext, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import ToastMsg from '../ui/toast/ToastMsg';
import Select from '../ui/select/Select';
import InfiniteCourses from './InfiniteCourses';

import { searchCourses } from './action';

import type { TCourse } from '@/types/response';

// 최신순 오래된순 평점순 이름순
const sortOptions = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  {
    label: '별점순',
    value: 'rate',
  },
  // {
  //   label: '이름순',
  //   value: 'name',
  // },
];

function checkBatchimEnding(word: string) {
  const lastLetter = word[word.length - 1];
  const uni = lastLetter.charCodeAt(0);

  if (uni < 44032 || uni > 55203) return false;

  return (uni - 44032) % 28 != 0;
}

export default function CourseResults({
  data,
}: {
  data: { courses: TCourse[]; hasNext: boolean };
}) {
  const [state, dispatch] = useContext(SearchContext);
  const [error, setError] = useState('');

  async function onChangeSelectOption(option: string) {
    dispatch({ type: 'SET_LOADING', payload: true });

    const result = await searchCourses(state.keyword, 1, 5, option);

    if (result.status === 'failed') {
      setError(result.message);
      dispatch({ type: 'SET_LOADING', payload: false });
      setTimeout(() => setError(''), 2000);
      return;
    }

    dispatch({ type: 'SEARCH_COURSE', payload: result.data });
    dispatch({ type: 'SET_SORT_OPTION', payload: option });
    dispatch({ type: 'SET_LOADING', payload: false });
  }

  return (
    <div className="py-4 flex flex-col grow">
      <span className="px-4 text-gray-400 font-extralight">{`'${
        state.currentTerm
      }'${
        checkBatchimEnding(state.currentTerm) ? '이' : '가'
      } 포함된 코스를 모두 검색하였습니다.`}</span>
      <div className="px-4 flex justify-end">
        <Select
          list={sortOptions}
          selectOption={state.sortOptions}
          onChangeSelectOption={onChangeSelectOption}
          defaultValue={sortOptions[0].label}
        />
      </div>
      <InfiniteCourses
        initialContent={data.courses}
        initialHasNext={data.hasNext}
      />
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </div>
  );
}
