import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import HomeNavigation from './HomeNavigation';

import type { ReactNode } from 'react';
import { myInfoSchema } from '@/types/response';
import MemberProvider, { TMemberStatus } from '@/context/MemberContext';

export default async function HomeLayout({
  children,
}: {
  children: ReactNode;
}) {
  const memberJson = await authenticateUser();
  const memberResult = myInfoSchema.safeParse(memberJson);

  const memberStatus: TMemberStatus = memberResult.success
    ? { member: memberResult.data, isLoggedIn: 'true' }
    : { isLoggedIn: 'false' };
  return (
    <MemberProvider status={memberStatus}>
      <section className="flex flex-col grow">
        <HomeNavigation />
        {children}
      </section>
    </MemberProvider>
  );
}
