'use client';
import React, { useEffect, useState } from 'react';
import { getNotificationList } from './notificationActions';
import {
  TNotification,
  notificationResponseSchema,
} from '@/types/notificationResponse';
import Select from '../ui/select/Select';

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
  const size = 5;
  const [notificationList, setNotificationList] = useState(notifications);
  const onChangeSelectOption = (option: number | string) => {
    if (typeof option === 'number') return;
    setSelectOption(option);
  };

  const getMoreNotifications = () => {};

  const getNotifications = (page: number, selectOption: string) => {
    return getNotificationList(page, size, selectOption);
  };
  useEffect(() => {
    const list = getNotifications(currentPage, selectOption);
    const parsed = notificationResponseSchema.safeParse(list);
    if (!parsed.success) {
      setNotificationList([]);
      setHasNext(false);
      return;
    }
    setNotificationList(parsed.data.notifications);
    setHasNext(parsed.data.has_next);
  }, [selectOption, currentPage]);

  useEffect(() => {
    setCurrentPage(1);
    getNotifications(1, selectOption);
  }, [selectOption]);

  return (
    <>
      <div className="flex justify-end mx-4">
        <Select
          list={notificationSelect}
          selectOption={selectOption}
          onChangeSelectOption={onChangeSelectOption}
          defaultValue="최신순"
        />
      </div>
    </>
  );
}
