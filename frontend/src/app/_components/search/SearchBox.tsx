'use client';

import { useEffect, useState } from 'react';

import SearchBar from './SearchBar';
import SearchHistory from './SearchHistory';
import SearchResult from './SearchResult';

import type { Dispatch, SetStateAction } from 'react';

const SEARCH_HISTORY_KEY = 'search-history';

function parseSearchHistory(historyStr: string | null) {
  if (historyStr === null) {
    return [];
  }

  return JSON.parse(historyStr) as Array<string>;
}

export default function SearchBox({
  showHistory,
  searchResults,
  search,
  setCurrentFoundPlace,
}: {
  showHistory?: boolean;
  searchResults: { name: string; roadAddress: string }[];
  search: (keyword: string) => Promise<void>;
  setCurrentFoundPlace: Dispatch<
    SetStateAction<
      | {
          name: string;
          roadAddress: string;
          coords: { lat: number; lng: number };
        }
      | undefined
    >
  >;
}) {
  const [showResult, setShowResult] = useState(false);
  const [searchHistory, setSearchHistory] = useState<string[]>(() => {
    if (typeof window !== 'undefined') {
      return parseSearchHistory(localStorage.getItem(SEARCH_HISTORY_KEY));
    }

    return [];
  });

  useEffect(() => {
    localStorage.setItem(SEARCH_HISTORY_KEY, JSON.stringify(searchHistory));
  }, [searchHistory]);

  // Event handlers
  function deleteHistory(result: string) {
    const nextHistory = searchHistory.filter((value) => value !== result);
    setSearchHistory(nextHistory);
  }

  function deleteHistoryAll() {
    setSearchHistory([]);
  }

  function addHistory(result: string) {
    if (!searchHistory.includes(result)) {
      const nextHistory = [...searchHistory, result];
      setSearchHistory(nextHistory);
    }
  }

  function openResults() {
    setShowResult(true);
  }

  function closeResults() {
    setShowResult(false);
  }

  const searchHistoryProps = {
    deleteHistory,
    deleteHistoryAll,
    searchHistory,
  };

  // 검색어 자동완성 기능 구현될 시 conditional rendering
  return (
    <section
      className={`flex flex-col bg-white gap-3 z-10 ${showResult && 'grow'}`}
    >
      <SearchBar
        search={search}
        addHistory={addHistory}
        openResults={openResults}
        closeResults={closeResults}
        cancellable
      />
      <hr />
      <div className="relative grow" aria-label="Result/History container">
        {showHistory && <SearchHistory {...searchHistoryProps} />}
        {showResult && (
          <SearchResult
            searchResults={searchResults}
            closeResults={closeResults}
            setCurrentFoundPlace={setCurrentFoundPlace}
          />
        )}
      </div>
    </section>
  );
}
