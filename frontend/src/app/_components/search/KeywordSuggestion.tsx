'use client';

import { useContext, useEffect, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { keywordSuggestions } from './action';
import { keywordsSchema } from '@/types/response';
import SearchItem from './SearchItem';

export default function KeywordSuggestion() {
  const { state, dispatch } = useContext(SearchContext);
  const [suggestions, setSuggestions] = useState<string[]>([]);

  async function suggest(keyword: string) {
    const json = await keywordSuggestions(keyword);
    const result = keywordsSchema.safeParse(json);

    if (result.success) {
      console.log(result.data.keywords);
      setSuggestions(result.data.keywords);
    }
  }

  useEffect(() => {
    dispatch({ type: 'SET_LOADING', payload: true });

    const delayDebounceTimer = setTimeout(() => {
      suggest(state.keyword);
    }, 1000);

    dispatch({ type: 'SET_LOADING', payload: false });

    return () => clearTimeout(delayDebounceTimer);
  }, [dispatch, state.keyword]);

  return (
    <section className="px-7 flex flex-col grow">
      {suggestions.map((suggestion, index) => (
        <SearchItem key={`${suggestion}-${index}`} item={suggestion} />
      ))}
    </section>
  );
}
