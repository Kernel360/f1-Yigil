import MyPageFavoriteRoutes from '@/app/_components/mypage/routeTabs/MyPageFavorite';
import { ReactNode } from 'react';

export default function MyPageFavoriteLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <>
      <div className="flex justify-end gap-x-4 my-4">
        <MyPageFavoriteRoutes />
      </div>
    </>
  );
}
