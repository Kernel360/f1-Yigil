'use client';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import { getNotificationList } from './notificationActions';
import { TNotification } from '@/types/notificationResponse';
import Select from '../ui/select/Select';
import useIntersectionObserver from '../hooks/useIntersectionObserver';
import { scrollToTop } from '@/utils';
import LoadingIndicator from '../LoadingIndicator';
import NotificationList from './NotificationList';
import ToastMsg from '../ui/toast/ToastMsg';

const notificationSelect = [
  { label: '최신순', value: 'desc' },
  { label: '오래된순', value: 'asc' },
];

export default function Notification({
  notifications,
  has_next,
}: {
  notifications: TNotification['notifications'];
  has_next: boolean;
}) {
  const [selectOption, setSelectOption] = useState<string>('desc');
  const [hasNext, setHasNext] = useState(has_next);
  const [currentPage, setCurrentPage] = useState(1);
  const [notificationList, setNotificationList] =
    useState<TNotification['notifications']>(notifications);
  const [isLoading, setIsLoading] = useState(false);
  const [errorText, setErrorText] = useState('');
  const size = 15;
  const ref = useRef(null);
  const onChangeSelectOption = (option: number | string) => {
    if (typeof option === 'number') return;
    setSelectOption(option);
  };
  const getMoreNotifications = async () => {
    setCurrentPage((prev) => (prev += 1));
    const notifications = await getNotificationList(
      currentPage + 1,
      size,
      selectOption,
    );
    if (!notifications.success) {
      setErrorText('알림 데이터를 불러오는데 실패했습니다.');
      setIsLoading(false);
      return;
    }
    setHasNext((prev) => (prev = notifications.data.has_next));
    setNotificationList((prev) => [
      ...prev,
      ...notifications.data.notifications,
    ]);
  };

  useIntersectionObserver(ref, getMoreNotifications, hasNext);

  const getNotifications = useCallback(
    async (page: number, size: number, selectOption: string) => {
      try {
        setIsLoading(true);
        const notifications = await getNotificationList(
          page,
          size,
          selectOption,
        );

        if (!notifications.success) {
          setNotificationList([]);
          setErrorText('알림 데이터를 불러오는데 실패했습니다.');
          setIsLoading(false);
          return;
        }
        setHasNext((prev) => (prev = notifications.data.has_next));
        setNotificationList(notifications.data.notifications);
      } catch (error) {
        setErrorText('알림 데이터를 불러오는데 실패했습니다.');
      } finally {
        setIsLoading(false);
      }
    },
    [currentPage],
  );

  useEffect(() => {
    setCurrentPage(1);
    scrollToTop();
    getNotifications(1, size, selectOption);
  }, [selectOption, getNotifications]);

  return (
    <div className="w-full flex flex-col px-4">
      <div className="flex justify-end">
        <Select
          selectStyle="p-2"
          list={notificationSelect}
          selectOption={selectOption}
          onChangeSelectOption={onChangeSelectOption}
          defaultValue="최신순"
        />
      </div>
      <ul className="flex flex-col gap-y-3">
        <NotificationList notifications={notificationList} />
      </ul>
      <div className="flex justify-center my-8" ref={ref}>
        {hasNext &&
          (isLoading ? (
            <LoadingIndicator loadingText="데이터 로딩중" />
          ) : (
            <button className="py-1 px-8 bg-gray-200 rounded-lg">더보기</button>
          ))}
      </div>
      {errorText && <ToastMsg title={errorText} timer={2000} id={Date.now()} />}
    </div>
  );
}
