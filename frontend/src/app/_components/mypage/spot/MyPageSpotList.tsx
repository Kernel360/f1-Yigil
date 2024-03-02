'use client';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import MyPageSpotItem from './MyPageSpotItem';
import MyPageSelectBtns from '../MyPageSelectBtns';
import Pagination from '../Pagination';
import CalendarIcon from '/public/icons/calendar.svg';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';
import LockIcon from '/public/icons/lock.svg';
import HamburgerIcon from '/public/icons/hamburger.svg';
import PlusIcon from '/public/icons/plus.svg';
import { TPopOverData } from '../../ui/popover/types';
import { EventFor } from '@/types/type';
import Dialog from '../../ui/dialog/Dialog';
import { TMyPageSpot } from '../types';
import { getMyPageSpots } from '../hooks/myPageActions';

export default function MyPageSpotList({
  placeList,
  totalPage,
}: {
  placeList: TMyPageSpot[];
  totalPage: number;
}) {
  const [allSpotList, setAllSpotList] = useState<TMyPageSpot[]>(placeList);
  const [checkedList, setCheckedList] = useState<
    { spot_id: TMyPageSpot['spot_id']; is_private: boolean }[]
  >([]);

  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;
  const [totalPageCount, setTotalPageCount] = useState(totalPage);

  const [selectOption, setSelectOption] = useState('all');
  const [sortOption, setSortOption] = useState<string>('desc');

  const [isDialogOpened, setIsDialogOpened] = useState(false);

  // currentPage가 바뀔 때 마다 새로운 데이터 호출
  useEffect(() => {
    getSpots(currentPage, divideCount, sortOption, selectOption);
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
      onClick: () => setIsDialogOpened(true),
    },
    {
      href: '/add/course',
      label: '일정 기록하기',
      icon: <CalendarIcon className="w-6 h-6" />,
    },
  ]);

  const getSpots = async (
    pageNum: number,
    size: number,
    sortOption: string,
    selectOption: string,
  ) => {
    const { content, total_pages } = await getMyPageSpots(
      pageNum,
      size,
      sortOption,
      selectOption,
    );
    setTotalPageCount(total_pages);
    setAllSpotList([...content]);
  };

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
    getSpots(1, divideCount, sortOption, selectOption);
  }, [selectOption, sortOption]);

  // 함수 분리 예정
  const onClickDelete = () => {
    setIsDialogOpened(true);
    // delete 로직
    // delete(checkedList)
  };

  const onClickUnLock = () => {
    // unLock or lock
  };

  const onClickLock = () => {};

  const closeDialog = () => {
    setIsDialogOpened(false);
  };

  const onChangeAllSpots = (
    e: EventFor<'input', 'onChange'>,
    setIsChecked: Dispatch<SetStateAction<boolean>>,
  ) => {
    if (e.currentTarget.checked) {
      const allSpots = allSpotList.map((spot) => {
        return { spot_id: spot.spot_id, is_private: spot.is_private };
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

  const onChangeSortOption = (option: string | number) => {
    if (typeof option === 'number') return;
    setAllSpotList([]);
    setSortOption(option);
    setCheckedList([]);
    setCurrentPage(1);
    getSpots(1, divideCount, option, selectOption);
  };

  const onChangeCheckedList = (
    spot_id: TMyPageSpot['spot_id'],
    is_private: boolean,
  ) => {
    if (!checkedList.length) setCheckedList([{ spot_id, is_private }]);
    else {
      // checkList 배열의 각 값을 확인 후 값이 없으면 체크 리스트 추가 값이 있으면 filter로 제거
      const found = checkedList.find((checked) => checked.spot_id === spot_id);

      if (!found) {
        setCheckedList([...checkedList, { spot_id, is_private }]);
      } else {
        const filteredList = checkedList.filter(
          (checkedId) => checkedId.spot_id !== spot_id,
        );
        setCheckedList(filteredList);
      }
    }
  };

  return (
    <>
      <div className="mt-4 mb-3 px-2">
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
          {isDialogOpened && (
            <Dialog
              text="기록을 삭제하시겠나요?"
              closeModal={closeDialog}
              handleConfirm={onClickDelete}
            />
          )}
          <FloatingActionButton
            popOverData={popOverData}
            openedIcon={<PlusIcon className="rotate-45 duration-200 z-30" />}
            closedIcon={<HamburgerIcon className="w-20 h-20" />}
          />
        </div>
      )}

      {allSpotList.map(({ spot_id, ...data }, idx) => (
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
  );
}
