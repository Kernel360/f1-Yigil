import MyPageContent from '@/app/_components/mypage/routeTabs/MyPageTabs';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import type { ReactNode } from 'react';
import { requestWithCookie } from '@/app/_components/api/httpRequest';

const url =
  process.env.NODE_ENV !== 'production'
    ? 'http://localhost:3000/endpoints/api/member'
    : 'https://yigil.co.kr/endpoints/api/member';

export default async function MyPageInformation({
  children,
}: {
  children: ReactNode;
}) {
  // const res = await fetch('http://localhost:8080/api/v1/members', {
  //   method: 'GET',
  //   headers: {
  //     Cookie: 'SESSION=id',
  //   },
  // });
  // const { data: memberInfo } = await res.json();

  const memberInfo = await requestWithCookie('members')()()()();
  console.log(memberInfo);

  return (
    <section className="w-full h-full flex flex-col">
      <MyPageInfo memberInfo={memberInfo} />
      <MyPageContent />
      {children}
    </section>
  );
}
