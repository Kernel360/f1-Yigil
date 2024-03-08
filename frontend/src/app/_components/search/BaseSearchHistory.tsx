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

  return (
    <section className="grow">
      <div className="flex justify-between">
        <p className="text-xl font-semibold">최근 검색어</p>
        <button
          className="text-gray-500"
          onClick={() => dispatch({ type: 'CLEAR_HISTORY' })}
        >
          모두 삭제
        </button>
      </div>
      <ul className="pl-3 py-4 grow">
        {state.histories.map((item, index) => (
          <SearchItem
            key={item}
            item={item}
            href="#"
            deleteResult={() =>
              dispatch({ type: 'DELETE_HISTORY', payload: index })
            }
            erasable
          />
        ))}
      </ul>
    </section>
  );
}
