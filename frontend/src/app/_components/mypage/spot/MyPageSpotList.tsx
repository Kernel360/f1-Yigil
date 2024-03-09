'use client';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import MyPageSpotItem from './MyPageSpotItem';
import MyPageSelectBtns from '../MyPageSelectBtns';
import Pagination from '../Pagination';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';
import LockIcon from '/public/icons/lock.svg';
import HamburgerIcon from '/public/icons/hamburger.svg';
import PlusIcon from '/public/icons/plus.svg';
import { TPopOverData } from '../../ui/popover/types';
import { EventFor } from '@/types/type';
import Dialog from '../../ui/dialog/Dialog';
import {
  changeOnPrivateMyTravel,
  changeOnPublicMyTravel,
  changeTravelsVisibility,
  deleteMySpot,
  getMyPageSpots,
} from '../hooks/myPageActions';
import { TMyPageSpot } from '@/types/myPageResponse';
import LoadingIndicator from '../../LoadingIndicator';

/**
 *
 * TODO: 로딩 처리 및 다이얼로그에 로딩 텍스트 수정되면 추가
 */

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

  const [loadingText, setLoadingText] = useState('');
  const [dialogText, setDialogText] = useState('');
  const [dialogState, setDialogState] = useState('');

  // currentPage가 바뀔 때 마다 새로운 데이터 호출
  useEffect(() => {
    getSpots(currentPage, divideCount, sortOption, selectOption);
  }, [currentPage, sortOption, selectOption, placeList]);

  const [popOverData, setPopOverData] = useState<TPopOverData[]>([
    {
      label: '기록 공개하기',
      icon: <UnLockIcon className="w-6 h-6" />,
      onClick: () => {
        setDialogText('기록을 공개하시겠나요?');
        setDialogState('unlock');
        setIsDialogOpened(true);
      },
    },
    {
      label: '기록 삭제하기',
      icon: <TrashIcon className="w-6 h-6" />,
      onClick: () => {
        setDialogText('기록을 삭제하시겠나요?');
        setDialogState('delete');
        setIsDialogOpened(true);
      },
    },
    // {
    //   href: '/add/course',
    //   label: '일정 기록하기',
    //   icon: <CalendarIcon className="w-6 h-6" />,
    // },
  ]);

  const getSpots = async (
    pageNum: number,
    size: number,
    sortOption: string,
    selectOption: string,
  ) => {
    const spotList = await getMyPageSpots(
      pageNum,
      size,
      sortOption,
      selectOption,
    );
    if (!spotList.success) {
      setAllSpotList([]);
      return;
    }
    setTotalPageCount(spotList.data.total_pages);
    setAllSpotList([...spotList.data.content]);
  };

  useEffect(() => {
    if (selectOption === 'all') {
      setPopOverData([
        {
          label: '기록 나만보기',
          icon: <LockIcon className="w-6 h-6 stroke-black" />,
          onClick: () => {
            setDialogText('기록을 잠금 설정하시겠나요?');
            setDialogState('lock');
            setIsDialogOpened(true);
          },
        },
        {
          label: '기록 삭제하기',
          icon: <TrashIcon className="w-6 h-6" />,
          onClick: () => {
            setDialogText('기록을 삭제하시겠나요?');
            setDialogState('delete');
            setIsDialogOpened(true);
          },
        },
        // {
        //   label: '일정 기록하기',
        //   href: '/add/course',
        //   icon: <CalendarIcon className="w-6 h-6" />,
        // },
      ]);
    } else if (selectOption === 'public') {
      setPopOverData([
        {
          label: '기록 나만보기',
          icon: <LockIcon className="w-6 h-6 stroke-black" />,
          onClick: () => {
            setDialogText('기록을 잠금 설정하시겠나요?');
            setDialogState('lock');
            setIsDialogOpened(true);
          },
        },
        {
          label: '기록 삭제하기',
          icon: <TrashIcon className="w-6 h-6" />,
          onClick: () => {
            setDialogText('기록을 삭제하시겠나요?');
            setDialogState('delete');
            setIsDialogOpened(true);
          },
        },
        // {
        //   label: '일정 기록하기',
        //   href: '/add/course',
        //   icon: <CalendarIcon className="w-6 h-6" />,
        // },
      ]);
    } else {
      setPopOverData([
        {
          label: '기록 공개하기',
          icon: <UnLockIcon className="w-6 h-6 stroke-black" />,
          onClick: () => {
            setDialogText('기록을 잠금 해제하시겠나요?');
            setDialogState('unlock');
            setIsDialogOpened(true);
          },
        },
        {
          label: '기록 삭제하기',
          icon: <TrashIcon className="w-6 h-6" />,
          onClick: () => {
            setDialogText('기록을 삭제하시겠나요?');
            setDialogState('delete');
            setIsDialogOpened(true);
          },
        },
      ]);
    }
  }, [selectOption, checkedList]);

  // 공개 여부에 따라 렌더링 할 spot list 정렬
  useEffect(() => {
    setCurrentPage(1);
    getSpots(1, divideCount, sortOption, selectOption);
  }, [selectOption, sortOption]);

  const onClickDelete = async (checkedIds: number[]) => {
    try {
      setLoadingText('삭제중 입니다');
      const promises = checkedIds.map((checkedId) => deleteMySpot(checkedId));
      await Promise.all(promises);
    } catch (error) {
      console.error(error);
    } finally {
      closeDialog();
    }
  };

  // 공개 비공개 처리 시 Dialog에 로딩 텍스트 전달하기
  const onClickUnLock = async () => {
    try {
      setLoadingText('잠금 해제중 입니다');
      const promises = checkedList.map((checked) =>
        changeOnPublicMyTravel(checked.spot_id),
      );
      await Promise.all(promises);
    } catch (error) {
      console.log(error);
    } finally {
      closeDialog();
    }
  };

  const onClickLock = async () => {
    try {
      setLoadingText('잠금 처리중 입니다');
      const promises = checkedList.map((checked) =>
        changeOnPrivateMyTravel(checked.spot_id),
      );
      await Promise.all(promises);
    } catch (error) {
      console.log(error);
    } finally {
      closeDialog();
    }
  };

  const handleDialogFunc = async () => {
    if (dialogState === 'delete') {
      onClickDelete(checkedList.map((checked) => checked.spot_id));
    } else if (dialogState === 'unlock') {
      onClickUnLock();
    } else {
      onClickLock();
    }
    setCheckedList([]);
  };

  // const onClickChangeVisibility = async () => {
  //   if (checkedList[0].is_private) {
  //     const ids = checkedList.map((checked) => checked.spot_id);
  //     const res = await changeTravelsVisibility(ids, true);
  //     console.log(res);
  //   } else {
  //     const ids = checkedList.map((checked) => checked.spot_id);
  //     const res = await changeTravelsVisibility(ids, false);
  //     console.log(res);
  //   }
  // };

  const closeDialog = () => {
    setIsDialogOpened(false);
  };

  const onChangeAllSpots = (
    e: EventFor<'input', 'onChange'>,
    setIsChecked: Dispatch<SetStateAction<boolean>>,
  ) => {
    if (e.currentTarget.checked) {
      if (selectOption === 'all') {
        const allSpots = allSpotList
          .map((spot) => {
            return { spot_id: spot.spot_id, is_private: spot.is_private };
          })
          .filter((spot) => !spot.is_private);
        setCheckedList(allSpots);
        setIsChecked(true);
      } else {
        const allSpots = allSpotList.map((spot) => {
          return { spot_id: spot.spot_id, is_private: spot.is_private };
        });
        setCheckedList(allSpots);
        setIsChecked(true);
      }
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
              text={dialogText}
              closeModal={closeDialog}
              handleConfirm={async () => handleDialogFunc()}
              loadingText={loadingText}
            />
          )}
          <FloatingActionButton
            popOverData={popOverData}
            openedIcon={<PlusIcon className="rotate-45 duration-200 z-30" />}
            closedIcon={<HamburgerIcon className="w-20 h-20" />}
          />
        </div>
      )}

      {allSpotList.map(({ spot_id, image_url, ...data }, idx) => (
        <MyPageSpotItem
          idx={idx}
          key={spot_id}
          spot_id={spot_id}
          image_url={image_url}
          {...data}
          checkedList={checkedList}
          onChangeCheckedList={onChangeCheckedList}
          selectOption={selectOption}
        />
      ))}
      {!!allSpotList.length && (
        <Pagination
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalPage={totalPageCount}
        />
      )}
    </>
  );
}
