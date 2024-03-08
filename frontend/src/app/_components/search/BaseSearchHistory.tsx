import { useContext, useEffect, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';

import SearchItem from './SearchItem';

export default function BaseSearchHistory() {
  const { state, dispatch } = useContext(SearchContext);

  // For two-pass rendering
  const [hasMounted, setHasMounted] = useState(false);
  useEffect(() => {
    setHasMounted(true);
  }, []);

  // Enable two-pass rendering
  if (!hasMounted) {
    return <></>;
  }

  const { histories } = state;

  return (
    <section className="grow flex flex-col">
      <div className="px-4 flex justify-between">
        <p className="pl-2 text-xl font-semibold">최근 검색어</p>
        <button
          className="text-gray-500"
          onClick={() => dispatch({ type: 'CLEAR_HISTORY' })}
        >
          모두 삭제
        </button>
      </div>
      {histories.length === 0 ? (
        <section className="grow flex flex-col justify-center items-center gap-8">
          <span className="text-6xl">🔍</span>
          <br />
          <span className="text-3xl">키워드를 입력해 검색하세요.</span>
        </section>
      ) : (
        <ul className="pl-8 pr-3 py-4 flex flex-col gap-4 grow">
          {histories.map((item) => (
            <SearchItem key={item} item={item} erasable />
          ))}
        </ul>
      )}
    </section>
  );
}
