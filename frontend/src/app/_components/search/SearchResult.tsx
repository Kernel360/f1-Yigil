import { Dispatch } from 'react';
import { TAddSpotAction } from '../add/spot/SpotContext';
import { httpRequest } from '../api/httpRequest';
import SearchIcon from '/public/icons/search.svg';

/** 배포용 추가 */
const coordsUrl =
  process.env.NODE_ENV !== 'production'
    ? 'http://localhost:3000/endpoints/api/coords'
    : 'https://yigil.co.kr/endpoints/api/coords';

const placeIdUrl =
  process.env.NODE_ENV !== 'production'
    ? 'http://localhost:3000/endpoints/api/placeId'
    : 'https://yigil.co.kr/endpoints/api/placeId';

export default function SearchResult({
  dispatchSpot,
  dispatchStep,
  searchResults,
}: {
  dispatchSpot: Dispatch<TAddSpotAction>;
  dispatchStep: Dispatch<{ type: 'next' } | { type: 'previous' }>;
  searchResults: { name: string; roadAddress: string }[];
}) {
  async function handleClick(name: string, roadAddress: string) {
    const res = await fetch(coordsUrl, {
      method: 'POST',
      body: JSON.stringify({ address: roadAddress }),
    });

    const coords = (await res.json()) as { lat: number; lng: number };

    const mapUrl = await httpRequest('places/static-image')(
      `?name=${name}&address=${roadAddress}`,
    )()()();

    const naverMapUrl = await fetch(placeIdUrl, {
      method: 'POST',
      body: JSON.stringify({ coords }),
    });
    console.log(naverMapUrl);

    const mapUrlFromBackend = mapUrl?.code ? mapUrl.map_static_image_url : '';

    const mapUrlFromNaver = '';

    dispatchSpot({ type: 'SET_NAME', payload: name });
    dispatchSpot({ type: 'SET_ADDRESS', payload: roadAddress });
    dispatchSpot({ type: 'SET_COORDS', payload: coords });

    if (mapUrlFromBackend) {
      dispatchSpot({ type: 'SET_SPOT_MAP_URL', payload: mapUrl });
    }

    dispatchStep({ type: 'next' });
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
