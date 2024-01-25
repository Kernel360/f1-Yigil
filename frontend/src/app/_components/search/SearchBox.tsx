'use client';

import { useSearchParams } from 'next/navigation';

import SearchBar from './SearchBar';

export default function SearchBox({ showHistory }: { showHistory?: boolean }) {
  const searchParams = useSearchParams();

  const showResult = showHistory || searchParams.size !== 0;

  return (
    <section className="flex flex-col grow">
      <SearchBar />
      <section className="grow"></section>
    </section>
  );
}
