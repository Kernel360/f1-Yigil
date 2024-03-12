import { getMyPageBookmarks } from '@/app/_components/mypage/bookmark/bookmarkActions';
import MyPageBookmarkList from '@/app/_components/mypage/bookmark/MyPageBookmarkList';

import { myPageBookmarkListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageBookmarkPage() {
  const bookmarkList = await getMyPageBookmarks();
  if (!bookmarkList.success) return <>failed</>;

  return (
    <>
      {!!bookmarkList.data.bookmarks.length ? (
        <MyPageBookmarkList bookmarkList={bookmarkList.data.bookmarks} />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          북마크를 추가해주세요.
        </div>
      )}
    </>
  );
}
