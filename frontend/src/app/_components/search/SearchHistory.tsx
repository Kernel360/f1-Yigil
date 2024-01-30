'use client';

import { useEffect, useState } from 'react';

import SearchItem from './SearchItem';

export default function SearchHistory({
  deleteResult,
  deleteResultAll,
  searchResults,
}: {
  deleteResult: (result: string) => void;
  deleteResultAll: () => void;
  searchResults: string[];
}) {
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
    <section className="mt-4 py-4 border-t-2">
      <div className="flex justify-between">
        <p className="text-xl font-semibold">최근 검색어</p>
        <button className="text-gray-500" onClick={() => deleteResultAll()}>
          모두 삭제
        </button>
      </div>
      <ul className="pl-3 py-4 grow">
        {searchResults.map((item) => (
          <SearchItem
            key={item}
            item={item}
            href="#"
            deleteResult={deleteResult}
            erasable
          />
        ))}
      </ul>
    </section>
  );
}
