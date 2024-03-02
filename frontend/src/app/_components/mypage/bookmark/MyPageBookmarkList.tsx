'use client';
import { TMyPageBookmark } from '@/types/myPageResponse';
import React, { useEffect, useState } from 'react';
import Select from '../../ui/select/Select';
import { getMyPageBookmarks } from './bookmarkActions';
import MyPageBookmarkItem from './MyPageBookmarkItem';

const sortOptions = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
  { label: '이름순', value: 'name' },
  { label: '별점순', value: 'rate' },
];

export default function MyPageBookmarkList({
  bookmarkList,
}: {
  bookmarkList: TMyPageBookmark[];
}) {
  const [sortOption, setSortOption] = useState('desc');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;

  const [allBookmarkList, setAllBookmarkList] = useState(bookmarkList);

  const onChangeSortOption = (option: string | number) => {
    if (typeof option === 'number') return;
    setSortOption(option);
  };

  const getBookmarks = async (
    pageNum: number,
    size: number,
    sortOrder: string,
  ) => {
    const bookmarkList = await getMyPageBookmarks(pageNum, size, sortOrder);
    if (!bookmarkList.success) {
      setAllBookmarkList([]);
      return;
    }

    setAllBookmarkList([...bookmarkList.data.content]);
  };

  useEffect(() => {
    getBookmarks(currentPage, divideCount, sortOption);
  }, [currentPage]);

  useEffect(() => {
    setCurrentPage(1);
    getBookmarks(1, divideCount, sortOption);
  }, [sortOption]);

  return (
    <div className="px-4">
      <div className="flex justify-end mt-6 mb-4">
        <Select
          list={sortOptions}
          optionStyle=""
          selectOption={sortOption}
          onChangeSelectOption={onChangeSortOption}
          defaultValue="desc"
        />
      </div>
      {!!allBookmarkList.length ? (
        <>
          {allBookmarkList.map(({ place_id, ...bookmark }, idx) => (
            <MyPageBookmarkItem
              key={place_id}
              idx={idx}
              place_id={place_id}
              {...bookmark}
            />
          ))}
        </>
      ) : (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          북마크를 추가해주세요.
        </div>
      )}
    </div>
  );
}
