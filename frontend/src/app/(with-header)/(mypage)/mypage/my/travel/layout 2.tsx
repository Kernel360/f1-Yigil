import type { ReactNode } from 'react';
import MyPageTravel from '@/app/_components/mypage/routeTabs/MyPageTravel';

export default function MyPageTravelLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <>
      <MyPageTravel />
      {children}
    </>
  );
}
