'use client';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import MyPageSpotItem from './MyPageSpotItem';
import MyPageSelectBtns from '../MyPageSelectBtns';
import Pagination from '../Pagination';
import { getMyPageSpots } from '../hooks/myPageActions';
import CalendarIcon from '/public/icons/calendar.svg';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';
import LockIcon from '/public/icons/lock.svg';
import HamburgerIcon from '/public/icons/hamburger.svg';
import PlusIcon from '/public/icons/plus.svg';
import { TPopOverData } from '../../ui/popover/types';
import { EventFor } from '@/types/type';

export interface TMyPageSpot {
  spot_id: number;
  image_url: string;
  rating: number;
  post_date: string;
  title: string;
  isSecret: boolean;
}

export default function MyPageSpotList({
  placeList,
  totalPage,
}: {
  placeList: TMyPageSpot[];
  totalPage: number;
}) {
  const [allSpotList, setAllSpotList] = useState<TMyPageSpot[]>(placeList);
  const [renderSpotList, setRenderSpotList] = useState<TMyPageSpot[]>([]);
  const [checkedList, setCheckedList] = useState<
    { spot_id: TMyPageSpot['spot_id']; isSecret: boolean }[]
  >([]);

  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;
  const [totalPageCount, setTotalPageCount] = useState(totalPage);

  const [selectOption, setSelectOption] = useState('all');
  const [sortOption, setSortOption] = useState<string>('desc');

  // currentPage가 바뀔 때 마다 새로운 데이터 호출
  useEffect(() => {
    getUser(currentPage, divideCount, sortOption, selectOption);
  }, [currentPage]);

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

  const getUser = async (
    pageNum: number,
    size: number,
    sortOption: string,
    selectOption: string,
  ) => {
    const { content, totalPage } = await getMyPageSpots(
      pageNum,
      size,
      sortOption,
      selectOption,
    );
    setTotalPageCount(totalPage);
    setAllSpotList([...content]);
  };

  // TODO: checkPopOverState 함수를 생성 인수로 (selectOption, checkedList)

  // 전체 스팟 리스트 불러오기 시 render도 변경
  useEffect(() => {
    setRenderSpotList(allSpotList);
  }, [allSpotList]);

  // pop over 데이터 변경을 위한 현재 체크된 아이템 잠금 데이터 여부 리스닝 함수
  useEffect(() => {
    if (selectOption === 'all') {
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
    } else if (selectOption === 'public') {
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
          icon: <UnLockIcon className="w-6 h-6 stroke-black" />,
          onClick: () => onClickUnLock(),
        },
        {
          label: '기록 삭제하기',
          icon: <TrashIcon className="w-6 h-6" />,
          onClick: () => onClickDelete(),
        },
      ]);
    }
  }, [selectOption, checkedList]);

  // 공개 여부에 따라 렌더링 할 spot list 정렬
  useEffect(() => {
    setCurrentPage(1);
    getUser(1, divideCount, 'desc', selectOption);
  }, [selectOption, sortOption]);

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
      const allSpots = allSpotList.map((spot) => {
        return { spot_id: spot.spot_id, isSecret: spot.isSecret };
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
    setSortOption('desc');
  };

  const onChangeSortOption = (option: string) => {
    setAllSpotList([]);
    setSortOption(option);
    setCheckedList([]);
    setCurrentPage(1);
    getUser(1, divideCount, option, selectOption);
  };

  const onChangeCheckedList = (
    spot_id: TMyPageSpot['spot_id'],
    isSecret: boolean,
  ) => {
    if (!checkedList.length) setCheckedList([{ spot_id, isSecret }]);
    else {
      // checkList 배열의 각 값을 확인 후 값이 없으면 체크 리스트 추가 값이 있으면 filter로 제거
      const found = checkedList.find((checked) => checked.spot_id === spot_id);

      if (!found) {
        setCheckedList([...checkedList, { spot_id, isSecret }]);
      } else {
        const filteredList = checkedList.filter(
          (checkedId) => checkedId.spot_id !== spot_id,
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
          sortOption={sortOption}
          onClickSelectOption={onClickSelectOption}
          onChangeSortOption={onChangeSortOption}
          onChangeAllList={onChangeAllSpots}
        />
      </div>

      {!!checkedList.length && (
        <div className="relative">
          <FloatingActionButton
            popOverData={popOverData}
            openedIcon={<PlusIcon className="rotate-45 duration-200 z-30" />}
            closedIcon={<HamburgerIcon className="w-20 h-20" />}
          />
        </div>
      )}

      {renderSpotList.map(({ spot_id, ...data }, idx) => (
        <MyPageSpotItem
          idx={idx}
          key={spot_id}
          spot_id={spot_id}
          {...data}
          checkedList={checkedList}
          onChangeCheckedList={onChangeCheckedList}
          selectOption={selectOption}
        />
      ))}
      <Pagination
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        totalPage={totalPageCount}
      />
    </>
  ) : (
    <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
      장소를 추가해주세요.
    </div>
  );
}
