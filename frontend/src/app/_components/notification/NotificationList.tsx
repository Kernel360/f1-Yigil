import { TNotification } from '@/types/notificationResponse';
import React, { Fragment, ReactNode, useMemo } from 'react';
import NotificationItem from './NotificationItem';

export default function NotificationList({
  notifications,
}: {
  notifications: TNotification['notifications'];
}) {
  const notificationElements = useMemo(
    () =>
      notifications.reduce(
        (
          acc: ReactNode[],
          {
            notification_id,
            sender_profile_image_url,
            sender_id,
            message,
            create_date,
            read,
          },
          idx,
          arr,
        ) => {
          // 이전 요소의 날짜와 현재 요소의 날짜를 비교
          const prevDate = idx > 0 ? arr[idx - 1].create_date : '';
          const currentDate = checkDate(create_date);
          const prevFormattedDate = idx > 0 ? checkDate(prevDate) : '';

          // 날짜가 달라질 때만 날짜 표시를 추가
          if (currentDate !== prevFormattedDate) {
            acc.push(
              <div key={`date-${create_date}-${idx}`} className="text-gray-500">
                {currentDate}
              </div>,
            );
          }

          // 메시지 아이템 추가
          acc.push(
            <Fragment key={`${message}-${create_date}-${idx}`}>
              <NotificationItem
                notification_id={notification_id}
                sender_profile_image_url={sender_profile_image_url}
                sender_id={sender_id}
                message={message}
                create_date={create_date}
                read={read}
              />
            </Fragment>,
          );

          return acc;
        },
        [],
      ),
    [notifications],
  );

  return <>{notificationElements}</>;
}
function checkDate(date: string) {
  const currentDate = new Date().getDate();
  const notificationDate = new Date(date).getDate();
  const subtractDate = currentDate - notificationDate;
  return subtractDate === 0 ? '오늘' : subtractDate === 1 ? '어제' : date;
}
