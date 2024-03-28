import { getMyPageBookmarks } from '@/app/_components/mypage/bookmark/bookmarkActions';
import MyPageBookmarkList from '@/app/_components/mypage/bookmark/BookmarkList';
import React from 'react';

export default async function MyPageBookmarkPage() {
  const bookmarkList = await getMyPageBookmarks();
  if (!bookmarkList.success)
    return (
      <>
        <div className="w-full h-full flex flex-col break-words justify-center items-center text-3xl text-center text-main">
          북마크를 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
        </div>
      </>
    );

  return (
    <>
      {!!bookmarkList.data.bookmarks.length ? (
        <MyPageBookmarkList
          bookmarkList={bookmarkList.data.bookmarks}
          has_next={bookmarkList.data.has_next}
        />
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          북마크를 추가해주세요.
        </div>
      )}
    </>
  );
}
