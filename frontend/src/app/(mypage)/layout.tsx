import MyPageRoutes from '../_components/mypage/MyPageRoutes';

export default function MyPageLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="mt-[100px]">
      <div className="flex items-center gap-x-4 my-4 ml-4">
        <MyPageRoutes />
      </div>
      {children}
    </div>
  );
}
