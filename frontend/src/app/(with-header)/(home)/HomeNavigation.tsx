'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';

export default function HomeNavigation() {
  const pathname = usePathname();

  const selected = 'border-b-4 border-black text-black';

  return (
    <nav className="mt-[80px] px-8 py-4 flex gap-4 text-3xl font-semibold text-gray-300">
      <Link className={`${pathname === '/' && selected}`} href="/">
        홈
      </Link>
      <Link className={`${pathname === '/nearby' && selected}`} href="/nearby">
        주변
      </Link>
    </nav>
  );
}
