'use client';
import React, { useEffect, useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import MyPageSpotItem from './MyPageSpotItem';
import CalendarIcon from '/public/icons/calendar.svg';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';
import LockIcon from '/public/icons/lock.svg';
import { TPopOverData } from '../../ui/popover/types';

export interface TMyPageSpot {
  postId: number;
  travel_id: number;
  image_url: string;
  rating: number;
  post_date: string;
  title: string;
  description: string;
  isSecret: boolean;
}

export default function MyPageSpotList({
  placeList,
}: {
  placeList: TMyPageSpot[];
}) {
  const [checkedList, setCheckedList] = useState<
    { postId: TMyPageSpot['postId']; isSecret: boolean }[]
  >([]);
  // 잠금 된 아이템들을 선택 시 true로 변경
  const [popOverDataIsSecretState, setPopOverDataIsSecretState] =
    useState(true);

  const [popOverData, setPopOverData] = useState<TPopOverData[]>([
    {
      label: '나만보기 풀기',
      icon: <UnLockIcon className="w-6 h-6" />,
      onClick: () => onClickUnLock(),
    },

    {
      href: '/add/course',
      label: '일정 기록하기',
      icon: <CalendarIcon className="w-6 h-6" />,
    },
  ]);
  useEffect(() => {
    if (!!checkedList.length && checkedList[0].isSecret)
      setPopOverDataIsSecretState(true);
    else setPopOverDataIsSecretState(false);
  }, [checkedList[0]]);

  useEffect(() => {
    setPopOverData(
      popOverData.map((item, idx) => {
        return {
          label: popOverDataIsSecretState ? '나만보기 풀기' : '나만보기 설정',
          icon: popOverDataIsSecretState ? (
            <UnLockIcon className="w-6 h-6" />
          ) : (
            <LockIcon className="w-6 h-6" />
          ),
          onClick: popOverDataIsSecretState
            ? () => onClickUnLock()
            : () => onClickLock(), // unlock or lock
        };
      }),
    );
  }, [popOverDataIsSecretState]);

  const onClickDelete = () => {
    // delete 로직
    // delete(checkedList)
  };

  const onClickUnLock = () => {
    // unLock or lock
  };

  const onClickLock = () => {};
  const filterCheckedList = (
    postId: TMyPageSpot['postId'],
    isSecret: boolean,
  ) => {
    if (!checkedList.length) setCheckedList([{ postId, isSecret }]);
    else {
      // checkList 배열의 각 값을 확인 후 값이 없으면 체크 리스트 추가 값이 있으면 filter로 제거
      checkedList.forEach((checked) => {
        if (checked.postId !== postId) {
          setCheckedList([...checkedList, { postId, isSecret }]);
        } else {
          const filteredList = checkedList.filter(
            (checkedId) => checkedId.postId !== postId,
          );
          setCheckedList(filteredList);
        }
      });
    }
  };

  return (
    <>
      <div className="flex justify-end">
        <select name="" id="" className="p-2 mx-4">
          <option value="">최신순</option>
          <option value="">오래된순</option>
          <option value="">평점순</option>
        </select>
      </div>
      <div className="relative">
        <FloatingActionButton popOverData={popOverData} />
      </div>
      {!!placeList.length &&
        placeList.map(({ postId, ...data }, idx) => (
          <MyPageSpotItem
            idx={idx}
            key={postId}
            postId={postId}
            {...data}
            checkedList={checkedList}
            filterCheckedList={filterCheckedList}
          />
        ))}
    </>
  );
}
