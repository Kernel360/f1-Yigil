import BackButton from '@/app/_components/place/BackButton';
import ReviewsNavigation from '@/app/_components/place/ReviewNavigation';

import type { ReactElement } from 'react';

export default function PlaceDetailLayout({
  params,
  children,
  reviews,
}: {
  params: { id: number };
  children: ReactElement;
  reviews: ReactElement;
}) {
  return (
    <main aria-label="place-detail" className="flex flex-col">
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">장소 상세</span>
      </nav>
      {children}
      <hr className="border-8" />
      <ReviewsNavigation id={params.id} />
      {reviews}
    </main>
  );
}
