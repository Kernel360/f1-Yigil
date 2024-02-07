import MyPageContent from '@/app/_components/mypage/routeTabs/MyPageTabs';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import { getServerSession } from 'next-auth';
import type { ReactNode } from 'react';
import { httpRequest } from '@/app/_components/api/httpRequest';

export default async function MyPageInformation({
  children,
}: {
  children: ReactNode;
}) {
  const getMemberInfo = await httpRequest('members')()()()();
  console.log(getMemberInfo);
  const session = await getServerSession();

  return (
    <section className="w-full h-full flex flex-col">
      <MyPageInfo />
      <MyPageContent />
      {children}
    </section>
  );
}
