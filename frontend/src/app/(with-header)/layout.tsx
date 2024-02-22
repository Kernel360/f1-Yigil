import type { ReactNode } from 'react';

import '../globals.css';
import { requestWithCookie } from '../_components/api/httpRequest';
import Header from '../_components/header/Header';

export default async function WithHeaderLayout({
  children,
}: {
  children: ReactNode;
}) {
  const memberInfo = await requestWithCookie('members')()()()();
  return (
    <section className="w-full h-full flex flex-col">
      <Header memberInfo={memberInfo.code ? null : memberInfo} />
      {children}
    </section>
  );
}
