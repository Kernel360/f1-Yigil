import type { ReactNode } from 'react';
import '../globals.css';
import Header from '../_components/header/Header';

export default async function WithHeaderLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <section className="w-full h-full flex flex-col">
      <Header />
      {children}
    </section>
  );
}
