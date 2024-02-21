'use client';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import MyPageSpotItem from './MyPageSpotItem';
import CalendarIcon from '/public/icons/calendar.svg';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';
import LockIcon from '/public/icons/lock.svg';
import { TPopOverData } from '../../ui/popover/types';
import { EventFor } from '@/types/type';
import MyPageSelectBtns from '../MyPageSelectBtns';

import { getMyPageSpot } from '../hooks/useMyPage';

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
  const [spotList, setSpotList] = useState<TMyPageSpot[]>([]);
  const [checkedList, setCheckedList] = useState<
    { postId: TMyPageSpot['postId']; isSecret: boolean }[]
  >([]);
  const [selectOption, setSelectOption] = useState('전체');

  const [sortOption, setSortOption] = useState<
    '최신순' | '오래된순' | '별점순'
  >('최신순');

  const [popOverData, setPopOverData] = useState<TPopOverData[]>([
    {
      label: '기록 공개하기',
      icon: <UnLockIcon className="w-6 h-6" />,
      onClick: () => onClickUnLock(),
    },
    {
      label: '기록 삭제하기',
      icon: <TrashIcon className="w-6 h-6" />,
      onClick: () => onClickUnLock(),
    },
    {
      href: '/add/course',
      label: '일정 기록하기',
      icon: <CalendarIcon className="w-6 h-6" />,
    },
  ]);
  // const getUser = async () => {
  //   // const result = await getMyPageSpot();
  //   const res = await httpRequest('members')()()()();
  //   // console.log(result);
  //   console.log(res);
  // };
  // useEffect(() => {
  //   getUser();
  // }, []);

  // TODO: checkPopOverState 함수를 생성 인수로 (selectOption, checkedList)

  // pop over 데이터 변경을 위한 현재 체크된 아이템 잠금 데이터 여부 리스닝 함수
  useEffect(() => {
    if (selectOption === '전체') {
      if (checkedList.length > 1) {
        setPopOverData([
          {
            label: '기록 나만보기',
            icon: <LockIcon className="w-6 h-6 stroke-black" />,
            onClick: () => onClickLock(),
          },
          {
            label: '기록 삭제하기',
            icon: <TrashIcon className="w-6 h-6" />,
            onClick: () => onClickDelete(),
          },
          {
            label: '일정 기록하기',
            href: '/add/course',
            icon: <CalendarIcon className="w-6 h-6" />,
          },
        ]);
      } else {
        setPopOverData([
          {
            label: '기록 공개하기',
            icon: <UnLockIcon className="w-6 h-6" />,
            onClick: () => onClickUnLock(),
          },
          {
            label: '기록 삭제하기',
            icon: <TrashIcon className="w-6 h-6" />,
            onClick: () => onClickDelete(),
          },
        ]);
      }
    } else if (selectOption === '공개') {
      if (checkedList.length > 1) {
        setPopOverData([
          {
            label: '기록 나만보기',
            icon: <LockIcon className="w-6 h-6" />,
            onClick: () => onClickLock(),
          },
          {
            label: '기록 삭제하기',
            icon: <TrashIcon className="w-6 h-6" />,
            onClick: () => onClickDelete(),
          },
          {
            label: '일정 기록하기',
            href: '/add/course',
            icon: <CalendarIcon className="w-6 h-6" />,
          },
        ]);
      } else {
        setPopOverData([
          {
            label: '기록 나만보기',
            icon: <LockIcon className="w-6 h-6" />,
            onClick: () => onClickUnLock(),
          },
          {
            label: '기록 삭제하기',
            icon: <TrashIcon className="w-6 h-6" />,
            onClick: () => onClickDelete(),
          },
        ]);
      }
    } else {
      setPopOverData([
        {
          label: '기록 공개하기',
          icon: <LockIcon className="w-6 h-6" />,
          onClick: () => onClickUnLock(),
        },
        {
          label: '기록 삭제하기',
          icon: <TrashIcon className="w-6 h-6" />,
          onClick: () => onClickDelete(),
        },
      ]);
    }
  }, [checkedList[0], checkedList.length]);

  // 공개 여부에 따라 렌더링 할 spot list 정렬
  useEffect(() => {
    if (selectOption === '공개') {
      const isPublicList = placeList.filter((place) => !place.isSecret);
      setSpotList(isPublicList);
    } else if (selectOption === '비공개') {
      const isSecretList = placeList.filter((place) => place.isSecret);
      setSpotList(isSecretList);
    } else {
      setSpotList(placeList);
    }
  }, [selectOption]);

  // 함수 분리 예정
  const onClickDelete = () => {
    // delete 로직
    // delete(checkedList)
  };

  const onClickUnLock = () => {
    // unLock or lock
  };

  const onClickLock = () => {};

  const onChangeAllSpots = (
    e: EventFor<'input', 'onChange'>,
    setIsChecked: Dispatch<SetStateAction<boolean>>,
  ) => {
    if (e.currentTarget.checked) {
      const allSpots = spotList.map((spot) => {
        return { postId: spot.postId, isSecret: spot.isSecret };
      });
      setCheckedList(allSpots);
      setIsChecked(true);
    } else {
      setCheckedList([]);
      setIsChecked(false);
    }
  };

  const onClickSelectOption = (option: string) => {
    setSelectOption(option);
    setCheckedList([]);
  };

  const onChangeCheckedList = (
    postId: TMyPageSpot['postId'],
    isSecret: boolean,
  ) => {
    if (!checkedList.length) setCheckedList([{ postId, isSecret }]);
    else {
      // checkList 배열의 각 값을 확인 후 값이 없으면 체크 리스트 추가 값이 있으면 filter로 제거
      const found = checkedList.find((checked) => checked.postId === postId);

      if (!found) {
        setCheckedList([...checkedList, { postId, isSecret }]);
      } else {
        const filteredList = checkedList.filter(
          (checkedId) => checkedId.postId !== postId,
        );
        setCheckedList(filteredList);
      }
    }
  };

  return !!placeList.length ? (
    <>
      <div className="my-4">
        <MyPageSelectBtns
          selectOption={selectOption}
          onClickSelectOption={onClickSelectOption}
          // onClickSortOption={onClickSortOption}
          onChangeAllList={onChangeAllSpots}
        />
      </div>

      {!!checkedList.length && (
        <div className="relative">
          <FloatingActionButton popOverData={popOverData} />
        </div>
      )}

      {spotList.map(({ postId, ...data }, idx) => (
        <MyPageSpotItem
          idx={idx}
          key={postId}
          postId={postId}
          {...data}
          checkedList={checkedList}
          onChangeCheckedList={onChangeCheckedList}
          selectOption={selectOption}
        />
      ))}
    </>
  ) : (
    <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
      장소를 추가해주세요.
    </div>
  );
}
