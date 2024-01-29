'use client';

import { useSearchParams } from 'next/navigation';

import SearchBar from './SearchBar';
import SearchHistory from './SearchHistory';

export default function SearchBox({ showHistory }: { showHistory?: boolean }) {
  const searchParams = useSearchParams();

  return (
    <section className="flex flex-col grow">
      <SearchBar cancellable />
      <div className="grow">
        {showHistory ? searchParams.size === 0 && <SearchHistory /> : <></>}
      </div>
    </section>
  );
}
