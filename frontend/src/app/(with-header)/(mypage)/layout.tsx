import MyPageContent from '@/app/_components/mypage/MyPageContent';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import MyPageRoutes from '@/app/_components/mypage/MyPageRoutes';

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
