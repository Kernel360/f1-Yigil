import MyPageContent from '@/app/_components/mypage/routeTabs/MyPageTabs';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import type { ReactNode } from 'react';
import { authenticateUser } from '@/app/_components/mypage/hooks/myPageActions';

export default async function MyPageInformation({
  children,
}: {
  children: ReactNode;
}) {
  // const memberInfo = await authenticateUser();

  return (
    <section className="w-full h-full flex flex-col">
      {/* <MyPageInfo memberInfo={memberInfo} /> */}
      <MyPageContent />
      {children}
    </section>
  );
}
