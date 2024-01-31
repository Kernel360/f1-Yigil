'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';

import SearchIcon from '/public/icons/search.svg';

// 메뉴가 늘어나지 않을 것으로 상정하고 스타일 지정 조건을 하드코딩하였음
export default function HomeNavigation() {
  const pathname = usePathname();
  const isHomeOrSearch = pathname === '/' || pathname === '/search';

  const selected = 'border-b-4 border-black text-black';

  return (
    <nav className="px-8 py-4 text-3xl font-semibold text-gray-300 flex justify-between items-center">
      <div className="flex gap-4">
        <Link className={`${isHomeOrSearch && selected}`} href="/">
          홈
        </Link>
        <Link
          className={`${pathname === '/nearby' && selected}`}
          href="/nearby"
        >
          주변
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
