import Link from 'next/link';

import type { ReactNode } from 'react';

export default function HomeLayout({ children }: { children: ReactNode }) {
  return (
    <>
      <nav className="mt-[80px] px-8 py-4 flex gap-4 text-2xl">
        <Link href="/">홈</Link>
        <Link href="/nearby">주변</Link>
      </nav>
      {children}
    </>
  );
}
