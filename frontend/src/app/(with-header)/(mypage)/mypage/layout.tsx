import { myPageTabs } from '@/app/_components/mypage/constants';
import MyPageContent from '@/app/_components/mypage/routeTabs/MyPageTabs';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import MyPageRoutes from '@/app/_components/mypage/routeTabs/MyPageRoutes';
import MyPageMyPlace from '@/app/_components/mypage/routeTabs/MyPagePlace';
import { getServerSession } from 'next-auth';
import type { ReactNode } from 'react';

export default async function MyPageInformation({
  children,
}: {
  children: ReactNode;
}) {
  const session = await getServerSession();

  return (
    <section className="w-full h-full flex flex-col">
      <MyPageInfo />
      <MyPageContent />
      {children}
    </section>
  );
}
