'use client';

import { useEffect, useState } from 'react';

import SearchBar from './SearchBar';
import SearchHistory from './SearchHistory';
import SearchResult from './SearchResult';

import type { Dispatch } from 'react';
import type { TAddSpotAction } from '../add/spot/SpotContext';

const SEARCH_HISTORY_KEY = 'search-history';

function parseSearchHistory(historyStr: string | null) {
  if (historyStr === null) {
    return [];
  }

  return JSON.parse(historyStr) as Array<string>;
}

export default function SearchBox({
  showHistory,
  dispatchSpot,
  dispatchStep,
  searchResults,
  search,
}: {
  showHistory?: boolean;
  dispatchSpot: Dispatch<TAddSpotAction>;
  dispatchStep: Dispatch<{ type: 'next' } | { type: 'previous' }>;
  searchResults: { name: string; roadAddress: string }[];
  search: (keyword: string) => Promise<void>;
}) {
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

  const searchHistoryProps = {
    deleteHistory,
    deleteHistoryAll,
    searchHistory,
  };

  // 검색어 자동완성 기능 구현될 시 conditional rendering
  return (
    <section className="flex flex-col">
      <SearchBar search={search} addHistory={addHistory} cancellable />
      <div className="grow" aria-label="Result/History container">
        {showHistory && <SearchHistory {...searchHistoryProps} />}
        {searchResults.length !== 0 && (
          <SearchResult
            dispatchSpot={dispatchSpot}
            dispatchStep={dispatchStep}
            searchResults={searchResults}
          />
        )}
      </div>
    </section>
  );
}
