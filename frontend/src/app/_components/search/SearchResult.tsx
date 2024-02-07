import { Dispatch } from 'react';
import { TAddSpotAction } from '../add/spot/SpotContext';
import { getCoords } from '../naver-map/hooks/getCoords';

export default function SearchResult({
  dispatch,
  searchResults,
}: {
  dispatch: Dispatch<TAddSpotAction>;
  searchResults: { name: string; roadAddress: string }[];
}) {
  async function handleClick(name: string, roadAddress: string) {
    const coords = await getCoords(roadAddress);

    dispatch({ type: 'SET_NAME', payload: name });
    dispatch({ type: 'SET_ADDRESS', payload: roadAddress });
    dispatch({ type: 'SET_COORDS', payload: coords });
  }

  return (
    <section className="relative h-[600px]">
      <ul className="absolute top-5 w-full h-[] z-10 bg-white flex flex-col grow">
        {searchResults.map(({ name, roadAddress }, index) => (
          <button
            className="flex"
            onClick={() => handleClick(name, roadAddress)}
            key={`${name}-${index}`}
          >
            {name}
          </button>
        ))}
      </ul>
    </section>
  );
}
