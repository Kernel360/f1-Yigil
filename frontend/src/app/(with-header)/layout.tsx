import Header from '@/app/_components/header/Header';

import type { ReactNode } from 'react';

import '../globals.css';

export default function WithHeaderLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <section className="w-full flex flex-col">
      <Header />
      {children}
    </section>
  );
}
