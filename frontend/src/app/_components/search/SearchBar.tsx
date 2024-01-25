'use client';

import { usePathname, useRouter, useSearchParams } from 'next/navigation';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

function CancelButton() {
  return (
    <button className="p-1 bg-gray-400 rounded-full flex justify-center items-center">
      <XMarkIcon className="w-3 h-3 stroke-white stroke-[1.25]" />
    </button>
  );
}

export default function SearchBar() {
  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { replace } = useRouter();

  function handleSearch(term: string) {
    const params = new URLSearchParams(searchParams);

    if (term) {
      params.set('keyword', term);
    } else {
      params.delete('keyword');
    }

    replace(`${pathname}?${params.toString()}`);
  }

  return (
    <div className="mx-4 px-4 py-2 bg-[#e5e7eb] shadow-xl rounded-full flex gap-4 items-center">
      <input
        className="w-full text-lg grow border-0 bg-transparent outline-none focus:border-b-2 focus:border-black"
        type="text"
        placeholder="검색어를 입력하세요."
        onChange={(event) => handleSearch(event.target.value)}
        defaultValue={searchParams.get('keyword')?.toString()}
      />
      {searchParams.size !== 0 && <CancelButton />}
      <SearchIcon className="w-6 h-6 shrink-0" />
    </div>
  );
}
