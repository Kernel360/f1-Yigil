'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

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
          <div className="flex justify-between" key={item}>
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
