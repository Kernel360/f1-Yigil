import type { ReactNode } from 'react';
import '../globals.css';
import Header from '../_components/header/Header';
import { authenticateUser } from '../_components/mypage/hooks/myPageActions';

export default async function WithHeaderLayout({
  children,
}: {
  children: ReactNode;
}) {
  const memberInfo = await authenticateUser();

  return (
    <section className="w-full h-full flex flex-col">
      <Header memberInfo={memberInfo?.code ? null : memberInfo} />
      {children}
    </section>
  );
}
