import MyPagePlace from '@/app/_components/mypage/routeTabs/MyPagePlace';
import type { ReactNode } from 'react';

export default async function MyPageInformation({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <>
      <MyPagePlace />
      {children}
    </>
  );
}
