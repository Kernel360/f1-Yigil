import MyPageRoutes from '@/app/_components/mypage/routeTabs/MyPageRoutes';
import { MemberContext } from '@/context/MemberContext';
import { useContext } from 'react';

export default function MyPageLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <div className="flex items-center gap-x-4 my-4 ml-4">
        <MyPageRoutes />
      </div>
      {children}
    </>
  );
}
