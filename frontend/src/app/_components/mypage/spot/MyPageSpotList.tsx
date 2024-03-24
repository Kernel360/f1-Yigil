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
  deleteMySpot,
  getMyPageSpots,
} from '../hooks/myPageActions';
import { TMyPageSpot } from '@/types/myPageResponse';
import LoadingIndicator from '../../LoadingIndicator';
import ToastMsg from '../../ui/toast/ToastMsg';

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
  const [isBackendDataLoading, setIsBackendDataLoading] = useState(false);
  const [errorText, setErrorText] = useState('');

  useEffect(() => {
    getSpots(currentPage, divideCount, sortOption, selectOption);
  }, [currentPage, sortOption, selectOption, placeList]);

  const [popOverData, setPopOverData] = useState<TPopOverData[]>([
    {
      label: '',
    },
  ]);

  const getSpots = async (
    pageNum: number,
    size: number,
    sortOption: string,
    selectOption: string,
  ) => {
    try {
      setIsBackendDataLoading(true);
      const spotList = await getMyPageSpots(
        pageNum,
        size,
        sortOption,
        selectOption,
      );
      if (!spotList.success) {
        setAllSpotList([]);
        setErrorText('데이터를 불러오는데 실패했습니다');
        return;
      }
      setTotalPageCount(spotList.data.total_pages);
      setAllSpotList([...spotList.data.content]);
    } catch (error) {
      setErrorText('데이터를 불러오는데 실패했습니다');
    } finally {
      setIsBackendDataLoading(false);
    }
  };

  useEffect(() => {
    if (selectOption === ('all' || 'public')) {
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
      setErrorText('삭제에 실패했습니다');
    } finally {
      closeDialog();
    }
  };

  const onClickUnLock = async () => {
    try {
      setLoadingText('잠금 해제중 입니다');
      const promises = checkedList.map((checked) =>
        changeOnPublicMyTravel(checked.spot_id),
      );
      await Promise.all(promises);
    } catch (error) {
      setErrorText('잠금 해제에 실패했습니다');
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
      setErrorText('잠금 처리에 실패했습니다');
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

  const onChangeSortOption = (option: string) => {
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

  return isBackendDataLoading ? (
    <div className="max-w-[430px] mx-auto mt-10">
      <LoadingIndicator loadingText="데이터 로딩중입니다" />
    </div>
  ) : (
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
            openedIcon={
              <PlusIcon className="w-9 h-9 rotate-45 duration-200 z-30" />
            }
            closedIcon={<HamburgerIcon className="w-12 h-12" />}
          />
        </div>
      )}
      {!allSpotList.length && selectOption === 'public' ? (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          공개 데이터가 없습니다.
        </div>
      ) : (
        !allSpotList.length &&
        selectOption === 'private' && (
          <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
            비공개 데이터가 없습니다.
          </div>
        )
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
      {!!allSpotList.length && (
        <Pagination
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalPage={totalPageCount}
        />
      )}
      {errorText && <ToastMsg title={errorText} timer={2000} />}
    </>
  );
}
