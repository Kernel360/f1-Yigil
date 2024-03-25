'use client';
import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import { TPopOverData } from '../../ui/popover/types';
import MyPageSelectBtns from '../MyPageSelectBtns';
import Pagination from '../Pagination';
import MyPageCourseItem from './MyPageCourseItem';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';
import LockIcon from '/public/icons/lock.svg';
import HamburgerIcon from '/public/icons/hamburger.svg';
import PlusIcon from '/public/icons/plus.svg';
import { TMyPageCourse } from '@/types/myPageResponse';
import {
  changeOnPrivateMyTravel,
  changeOnPublicMyTravel,
  deleteMyCourse,
  getMyPageCourses,
} from '../hooks/myPageActions';
import Dialog from '../../ui/dialog/Dialog';
import ToastMsg from '../../ui/toast/ToastMsg';
import LoadingIndicator from '../../LoadingIndicator';

export default function MyPageCourseList({
  placeList,
  totalPage,
}: {
  placeList: TMyPageCourse[];
  totalPage: number;
}) {
  const [allCourseList, setAllCourseList] =
    useState<TMyPageCourse[]>(placeList);
  const [checkedList, setCheckedList] = useState<
    { course_id: TMyPageCourse['course_id']; is_private: boolean }[]
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

  const [popOverData, setPopOverData] = useState<TPopOverData[]>([
    {
      label: '',
    },
  ]);

  useEffect(() => {
    getCourse(currentPage, divideCount, sortOption, selectOption);
  }, [currentPage, sortOption, selectOption, placeList]);

  const getCourse = async (
    pageNum: number,
    size: number,
    sortOption: string,
    selectOption: string,
  ) => {
    try {
      setIsBackendDataLoading(true);
      const courseList = await getMyPageCourses(
        pageNum,
        size,
        sortOption,
        selectOption,
      );
      if (!courseList.success) {
        setAllCourseList([]);
        setErrorText('데이터를 불러오는데 실패했습니다');
        return;
      }
      setTotalPageCount(courseList.data.total_pages);
      setAllCourseList([...courseList.data.content]);
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
    getCourse(1, divideCount, sortOption, selectOption);
  }, [selectOption, sortOption]);

  const onClickDelete = async (courseIds: number[]) => {
    try {
      setLoadingText('삭제중 입니다');
      const promises = courseIds.map((courseId) => deleteMyCourse(courseId));
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
        changeOnPublicMyTravel(checked.course_id),
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
        changeOnPrivateMyTravel(checked.course_id),
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
      onClickDelete(checkedList.map((checked) => checked.course_id));
    } else if (dialogState === 'unlock') {
      onClickUnLock();
    } else {
      onClickLock();
    }
    setCheckedList([]);
  };

  const closeDialog = () => {
    setIsDialogOpened(false);
  };

  const onClickSelectOption = (option: string) => {
    setSelectOption(option);
    resetCheckList();
    setSortOption('desc');
  };

  const resetCourseList = () => {
    setAllCourseList([]);
  };
  const resetCheckList = () => {
    setCheckedList([]);
  };

  const onChangeSortOption = (option: string) => {
    resetCourseList();
    setSortOption(option);
    resetCheckList();
    setCurrentPage(1);
    getCourse(1, divideCount, option, selectOption);
  };
  const onChangeAllCourse = (
    e: EventFor<'input', 'onChange'>,
    setIsChecked: Dispatch<SetStateAction<boolean>>,
  ) => {
    if (e.currentTarget.checked) {
      const allCourses = allCourseList.map((course) => {
        return { course_id: course.course_id, is_private: course.is_private };
      });
      setCheckedList(allCourses);
      setIsChecked(true);
    } else {
      resetCheckList();
      setIsChecked(false);
    }
  };

  const onChangeCheckedList = (
    course_id: TMyPageCourse['course_id'],
    is_private: boolean,
  ) => {
    if (!checkedList.length) setCheckedList([{ course_id, is_private }]);
    else {
      // checkList 배열의 각 값을 확인 후 값이 없으면 체크 리스트 추가 값이 있으면 filter로 제거
      const found = checkedList.find(
        (checked) => checked.course_id === course_id,
      );
      if (!found) {
        setCheckedList([...checkedList, { course_id, is_private }]);
      } else {
        const filteredList = checkedList.filter(
          (checkedId) => checkedId.course_id !== course_id,
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
      <div className="my-4 px-2">
        <MyPageSelectBtns
          selectOption={selectOption}
          sortOption={sortOption}
          onClickSelectOption={onClickSelectOption}
          onChangeSortOption={onChangeSortOption}
          onChangeAllList={onChangeAllCourse}
        />
      </div>
      {!!checkedList.length && (
        <div className="relative z-40">
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
      {!allCourseList.length && selectOption === 'public' ? (
        <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
          공개된 코스가 없습니다.
        </div>
      ) : (
        !allCourseList.length &&
        selectOption === 'private' && (
          <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
            비공개된 코스가 없습니다.
          </div>
        )
      )}
      {allCourseList.map(({ course_id, ...data }, idx) => (
        <MyPageCourseItem
          key={course_id}
          idx={idx}
          course_id={course_id}
          {...data}
          checkedList={checkedList}
          onChangeCheckedList={onChangeCheckedList}
          selectOption={selectOption}
        />
      ))}
      {!!allCourseList.length && (
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
