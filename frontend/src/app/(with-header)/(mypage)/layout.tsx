import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import MyPageRoutes from '@/app/_components/mypage/routeTabs/MyPageRoutes';
import MemberProvider, { TMemberStatus } from '@/context/MemberContext';
import { myInfoSchema } from '@/types/response';

export default async function MyPageLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const memberJson = await authenticateUser();
  const memberResult = myInfoSchema.safeParse(memberJson);

  const memberStatus: TMemberStatus = memberResult.success
    ? { member: memberResult.data, isLoggedIn: 'true' }
    : { isLoggedIn: 'false' };
  return (
    <>
      <MemberProvider status={memberStatus}>
        <div className="flex items-center gap-x-4 my-4 mx-4">
          <MyPageRoutes />
        </div>
        {children}
      </MemberProvider>
    </>
  );
}
