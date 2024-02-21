'use client';
import React, {
  Dispatch,
  SetStateAction,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';
import FloatingActionButton from '../../FloatingActionButton';
import MyPageSpotItem from './MyPageSpotItem';
import CalendarIcon from '/public/icons/calendar.svg';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';
import LockIcon from '/public/icons/lock.svg';
import { TPopOverData } from '../../ui/popover/types';
import { EventFor } from '@/types/type';
import MyPageSelectBtns from '../MyPageSelectBtns';
import { requestWithCookie } from '../../api/httpRequest';
import { getMyPageSpots, myPageSpotRequest } from '../hooks/myPageActions';
import Pagination from '../Pagination';

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
  const [allSpotList, setAllSpotList] = useState<TMyPageSpot[]>(placeList);
  const [publicSpotList, setPublicSpotList] = useState<TMyPageSpot[]>([]);
  const [privateSpotList, setPrivateSpotList] = useState<TMyPageSpot[]>([]);
  const [renderSpotList, setRenderSpotList] = useState<TMyPageSpot[]>([]);
  const [checkedList, setCheckedList] = useState<
    { postId: TMyPageSpot['postId']; isSecret: boolean }[]
  >([]);

  const [currentPage, setCurrentPage] = useState<number>(1);
  const divideCount = 5;
  const indexOfEnd = currentPage * divideCount;
  const indexOfStart = indexOfEnd - divideCount;
  const [isPageEnd, setIsPageEnd] = useState(false);

  const [selectOption, setSelectOption] = useState('all');
  const [sortOption, setSortOption] = useState<string>('desc');

  /** TODO: select option이 변경될 때 currentPage도 1로 변경
   */

  // currentPage가 바뀔 때 마다 새로운 데이터 호출
  useEffect(() => {
    getUser(currentPage, sortOption, selectOption);
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

  // TODO: sort 옵션과 select 옵션에 따른 별개 호출 로직 작성
  const getUser = async (
    pageNum: number,
    sortOption: string,
    selectOption: string,
  ) => {
    if (isPageEnd) return;
    const result = await fetch(
      `/api/v1/members/spot?page=${pageNum}&size=5&sortOrder=${
        sortOption === 'rate' ? `desc&sortBy=$${sortOption}` : sortOption
      }&selected=${selectOption}`,
    );
    const { data, last }: { data: TMyPageSpot[]; last: boolean } =
      await result.json();

    if (last) setIsPageEnd(true);
    setAllSpotList([...data]);

    // const res = await getMyPageSpots();

    // console.log(res);
  };
  // useEffect(() => {
  //   getUser(1, 'desc', selectOption);
  // }, []);

  // TODO: checkPopOverState 함수를 생성 인수로 (selectOption, checkedList)

  // 전체 스팟 리스트 불러오기 시 render도 변경
  useEffect(() => {
    setRenderSpotList(allSpotList);
  }, [allSpotList]);

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
    setCurrentPage(1);
    getUser(1, 'desc', selectOption);
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
      switch (selectOption) {
        case '공개':
          const publicSpots = publicSpotList.map((spot) => {
            return { postId: spot.postId, isSecret: spot.isSecret };
          });
          setCheckedList(publicSpots);
          setIsChecked(true);
          break;
        case '비공개':
          const privateSpots = privateSpotList.map((spot) => {
            return { postId: spot.postId, isSecret: spot.isSecret };
          });
          setCheckedList(privateSpots);
          setIsChecked(true);
          break;
        default:
          const allSpots = allSpotList.map((spot) => {
            return { postId: spot.postId, isSecret: spot.isSecret };
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
  };

  const onChangeSortOption = (option: string) => {
    setAllSpotList([]);
    setSortOption(option);
    // pageNumRef.current = 1;
    // getUser(pageNumRef.current, option);
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
          onChangeSortOption={onChangeSortOption}
          onChangeAllList={onChangeAllSpots}
        />
      </div>

      {!!checkedList.length && (
        <div className="relative">
          <FloatingActionButton popOverData={popOverData} />
        </div>
      )}

      {renderSpotList.map(({ postId, ...data }, idx) => (
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
      <Pagination />
    </>
  ) : (
    <div className="w-full h-full flex justify-center items-center text-4xl text-center text-main">
      장소를 추가해주세요.
    </div>
  );
}
