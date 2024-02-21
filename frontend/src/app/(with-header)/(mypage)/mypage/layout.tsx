import MyPageContent from '@/app/_components/mypage/routeTabs/MyPageTabs';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import { getServerSession } from 'next-auth';
import type { ReactNode } from 'react';

const url =
  process.env.NODE_ENV !== 'production'
    ? 'http://localhost:3000/endpoints/api/member'
    : 'https://yigil.co.kr/endpoints/api/member';

export default async function MyPageInformation({
  children,
}: {
  children: ReactNode;
}) {
  const getMemberInfo = await fetch(url);
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
