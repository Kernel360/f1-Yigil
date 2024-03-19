import Link from 'next/link';
import Places from './Places';
import { getPlaces } from './action';

import ChevronRightIcon from '/public/icons/chevron-right.svg';

export default async function RecommendedPlaces({
  isLoggedIn,
}: {
  isLoggedIn: boolean;
}) {
  const result = await getPlaces('recommended', 'more');

  if (result.status === 'failed') {
    return <>{result.message}</>;
  }

  return (
    <section className="flex flex-col" aria-label="recommended-places">
      <div className="flex justify-between items-center px-4 py-2">
        <span className="pl-4 text-3xl font-medium">맞춤 추천</span>
        <Link href="places/recommended">
          <ChevronRightIcon className="w-6 h-6 stroke-black stroke-2 [stroke-linecap:round] [stroke-linejoin:round]" />
        </Link>
      </div>
      <Places
        data={result.data}
        isLoggedIn={isLoggedIn}
        carousel
        variant="secondary"
      />
    </section>
  );
}
