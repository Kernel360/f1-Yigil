import SearchIcon from '/public/icons/search.svg';

import type { Dispatch, SetStateAction } from 'react';

export default function SearchResult({
  searchResults,
  closeResults,
  setCurrentFoundPlace,
}: {
  searchResults: { name: string; roadAddress: string }[];
  closeResults: () => void;
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
  /**
   * @todo 좌표 얻어오는 server action 추가
   */
  async function handleClick(name: string, roadAddress: string) {
    const coordsUrl =
      process.env.NODE_ENV !== 'production'
        ? 'http://localhost:3000/endpoints/api/coords'
        : 'https://yigil.co.kr/endpoints/api/coords';

    const res = await fetch(coordsUrl, {
      method: 'POST',
      body: JSON.stringify({ address: roadAddress }),
    });

    const coords = (await res.json()) as { lat: number; lng: number };

    setCurrentFoundPlace({ name, roadAddress, coords });

    closeResults();
  }

  return (
    <section className="relative h-[600px]">
      <ul className="absolute top-5 w-full z-10 bg-white flex flex-col grow gap-y-4 mt-4">
        {searchResults.map(({ name, roadAddress }, index) => (
          <button
            className="flex text-gray-900 gap-x-2 text-xl pl-2 my-2 hover:text-gray-500"
            onClick={() => handleClick(name, roadAddress)}
            key={`${name}-${index}`}
          >
            <SearchIcon />
            {name.length > 15 ? name.slice(0, 15) : name}
          </button>
        ))}
      </ul>
    </section>
  );
}
