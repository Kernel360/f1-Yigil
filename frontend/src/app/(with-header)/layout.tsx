import { getServerSession } from 'next-auth';
import type { ReactNode } from 'react';

import '../globals.css';
import Header from '../_components/header/Header';

export default async function WithHeaderLayout({
  children,
}: {
  children: ReactNode;
}) {
  const session = await getServerSession();
  return (
    <section className="w-full h-full flex flex-col">
      <Header session={session} />
      {children}
    </section>
  );
}
