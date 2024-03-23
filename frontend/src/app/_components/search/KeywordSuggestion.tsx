'use client';

import { useContext, useEffect, useState } from 'react';
import { SearchContext } from '@/context/search/SearchContext';
import { keywordSuggestions } from './action';
import { keywordsSchema } from '@/types/response';

import BackendSearchItem from './BackendSearchItem';

export default function KeywordSuggestion() {
  const [state, dispatch] = useContext(SearchContext);
  const [suggestions, setSuggestions] = useState<string[]>([]);

  async function suggest(keyword: string) {
    const json = await keywordSuggestions(keyword);
    const result = keywordsSchema.safeParse(json);

    if (result.success) {
      setSuggestions(result.data.keywords);
    }
  }

  useEffect(() => {
    dispatch({ type: 'SET_LOADING', payload: true });

    const delayDebounceTimer = setTimeout(() => {
      suggest(state.keyword);
    }, 500);

    dispatch({ type: 'SET_LOADING', payload: false });

    return () => clearTimeout(delayDebounceTimer);
  }, [dispatch, state.keyword]);

  return (
    <section className="px-7 flex flex-col grow gap-2">
      {suggestions.map((suggestion, index) => (
        <BackendSearchItem key={`${suggestion}-${index}`} name={suggestion} />
      ))}
    </section>
  );
}
