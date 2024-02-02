import MyPageMyPlace from '@/app/_components/mypage/MyPlace';
import type { ReactNode } from 'react';

export default async function MyPageInformation({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <>
      <MyPageMyPlace />
      {children}
    </>
  );
}
