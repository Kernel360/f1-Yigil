import BackButton from './BackButton';

import type { ReactElement } from 'react';

export default function PlaceDetailLayout({
  children,
  reviews,
}: {
  children: ReactElement;
  reviews: ReactElement;
}) {
  return (
    <main className="flex flex-col">
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">장소 상세</span>
      </nav>
      {children}
      <hr className="border-8" />
      {reviews}
    </main>
  );
}
