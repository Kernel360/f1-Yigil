import Notification from '@/app/_components/notification/Notification';

import React from 'react';
import { getNotificationList } from '../_components/notification/notificationActions';
import { notificationResponseSchema } from '@/types/notificationResponse';
import BackButton from '../_components/place/BackButton';
import Header from '../_components/header/Header';

async function NotificationPage() {
  const notifications = await getNotificationList();
  const parsed = notificationResponseSchema.safeParse(notifications);
  if (!parsed.success)
    return (
      <div className="w-full h-full flex flex-col break-keep justify-center items-center text-3xl text-center text-main">
        알림을 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
      </div>
    );
  return (
    <>
      <Notification
        notifications={parsed.data.notifications}
        has_next={parsed.data.has_next}
      />
    </>
  );
}

export default NotificationPage;
