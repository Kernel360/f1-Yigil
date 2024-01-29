'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

function parseSearchHistory(historyStr: string | null) {
  if (historyStr === null) {
    return [];
  }

  return JSON.parse(historyStr) as Array<string>;
}

export default function SearchHistory() {
  const [searchResults, setSearchResults] = useState<string[]>(() => {
    if (typeof window === 'undefined') {
      return [];
    }

    return parseSearchHistory(localStorage.getItem('search-history'));
  });

  function deleteResult(result: string) {
    const nextResults = searchResults.filter((value) => value !== result);
    setSearchResults(nextResults);
  }

  useEffect(() => {
    localStorage.setItem('search-history', JSON.stringify(searchResults));
  }, [searchResults]);

  return (
    <section className="mt-4 py-4 border-t-2">
      <div className="flex justify-between">
        <p className="text-xl font-semibold">최근 검색어</p>
        <button className="text-gray-500" onClick={() => setSearchResults([])}>
          모두 삭제
        </button>
      </div>
      <ul className="pl-3 py-4 grow">
        {searchResults.map((item) => (
          <div className="py-1 flex justify-between" key={item}>
            <Link className="flex py-3 grow items-center" href="#">
              <span className="flex gap-1 items-center">
                <SearchIcon className="w-6 h-6 mr-4" />
                {item}
              </span>
            </Link>
            <button className="p-3" onClick={() => deleteResult(item)}>
              <XMarkIcon className="w-4 h-4" />
            </button>
          </div>
        ))}
      </ul>
    </section>
  );
}
