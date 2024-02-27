import { getMyPageBookmarks } from '@/app/_components/mypage/bookmark/bookmarkActions';
import MyPageBookmarkList from '@/app/_components/mypage/bookmark/MyPageBookmarkList';

import { myPageBookmarkListSchema } from '@/types/myPageResponse';
import React from 'react';

export default async function MyPageBookmarkPage() {
  const bookmarkList = await getMyPageBookmarks();
  const parsedBookmark = myPageBookmarkListSchema.safeParse(
    bookmarkList.content,
  );

  if (!parsedBookmark.success) return <>failed</>;

  return (
    <>
      <MyPageBookmarkList
        bookmarkList={bookmarkList.content}
        totalPage={bookmarkList.totalPage}
      />
    </>
  );
}
