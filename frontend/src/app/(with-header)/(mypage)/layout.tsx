import MyPageRoutes from '@/app/_components/mypage/routeTabs/MyPageRoutes';

export default function MyPageLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <div className="flex items-center gap-x-4 my-4 mx-4">
        <MyPageRoutes />
      </div>
      {children}
    </>
  );
}
