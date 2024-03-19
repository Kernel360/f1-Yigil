import Link from 'next/link';
import Places from './Places';

import ChevronRightIcon from '/public/icons/chevron-right.svg';
import { getPlaces } from './action';

export default async function PopularPlaces({
  isLoggedIn,
}: {
  isLoggedIn: boolean;
}) {
  const result = await getPlaces('popular');

  if (result.status === 'failed') {
    return <>{result.message}</>;
  }

  return (
    <section className="flex flex-col" aria-label="popular-places">
      <div className="flex justify-between items-center px-4 py-2">
        <span className="pl-4 text-3xl font-medium">인기</span>
        <Link href="places/popular">
          <ChevronRightIcon className="w-6 h-6 stroke-black stroke-2 [stroke-linecap:round] [stroke-linejoin:round]" />
        </Link>
      </div>
      <Places
        data={result.data}
        isLoggedIn={isLoggedIn}
        variant="primary"
        carousel
      />
    </section>
  );
}
