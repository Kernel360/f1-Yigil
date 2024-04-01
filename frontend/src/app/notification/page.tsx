import React from 'react';
import { getNotificationList } from '../_components/notification/notificationActions';
import Notification from '../_components/notification/Notification';

async function NotificationPage() {
  const notifications = await getNotificationList();
  console.log(notifications);
  if (notifications.status === 'failed') throw new Error(notifications.message);

  return (
    <>
      {!!notifications.data.notifications.length ? (
        <Notification
          notifications={notifications.data.notifications}
          has_next={notifications.data.has_next}
        />
      ) : (
        <div className="w-full h-full flex flex-col break-keep justify-center items-center text-3xl text-center text-main">
          새로운 알림이 없습니다.
        </div>
      )}
    </>
  );
}

export default NotificationPage;
