'use client';

import Link from 'next/link';
import { usePathname, useSelectedLayoutSegment } from 'next/navigation';
import InfoIcon from '/public/icons/info.svg';
import SearchIcon from '/public/icons/search.svg';

export default function HomeNavigation() {
  const pathname = usePathname();
  const upperSegment = useSelectedLayoutSegment();

  const segmentsWhereHomeIsActive = ['search', 'places'];

  const isHomeActive =
    upperSegment === null ||
    segmentsWhereHomeIsActive.some((segment) =>
      pathname.slice(1).startsWith(segment),
    );

  const selected = 'border-b-4 border-black text-black';

  return (
    <nav className="px-4 py-4 text-3xl font-semibold text-gray-300 flex justify-between items-center">
      <div className="flex gap-6">
        <Link className={`${isHomeActive && selected}`} href="/">
          홈
        </Link>
        <Link
          className={`${pathname === '/nearby' && selected}`}
          href="/nearby"
        >
          지도
        </Link>
        <Link href="/privacy-policy" className="self-center ml-4">
          <InfoIcon className="fill-gray-500" />
        </Link>
      </div>
      {pathname === '/' && (
        <Link className="h-min" href="/search">
          <SearchIcon />
        </Link>
      )}
    </nav>
  );
}
