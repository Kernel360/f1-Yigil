'use client';

import { useEffect, useState } from 'react';
import { useSearchParams } from 'next/navigation';

import SearchBar from './SearchBar';
import SearchHistory from './SearchHistory';

const SEARCH_HISTORY_KEY = 'search-history';

function parseSearchHistory(historyStr: string | null) {
  if (historyStr === null) {
    return [];
  }

  return JSON.parse(historyStr) as Array<string>;
}

export default function SearchBox({ showHistory }: { showHistory?: boolean }) {
  const searchParams = useSearchParams();

  const [searchResults, setSearchResults] = useState<string[]>(() => {
    if (typeof window !== 'undefined') {
      return parseSearchHistory(localStorage.getItem(SEARCH_HISTORY_KEY));
    }

    return [];
  });

  useEffect(() => {
    localStorage.setItem(SEARCH_HISTORY_KEY, JSON.stringify(searchResults));
  }, [searchResults]);

  // Event handlers
  function deleteResult(result: string) {
    const nextResults = searchResults.filter((value) => value !== result);
    setSearchResults(nextResults);
  }

  function deleteResultAll() {
    setSearchResults([]);
  }

  function addResult(result: string) {
    const nextResults = [...searchResults, result];
    setSearchResults(nextResults);
  }

  const searchHistoryProps = {
    deleteResult,
    deleteResultAll,
    searchResults,
  };

  return (
    <section className="flex flex-col grow">
      <SearchBar addResult={addResult} cancellable />
      <div className="grow">
        {showHistory ? (
          searchParams.size === 0 && <SearchHistory {...searchHistoryProps} />
        ) : (
          <></>
        )}
      </div>
    </section>
  );
}
