import React from 'react';
import { getNotificationList } from '../_components/notification/notificationActions';
import Notification from '../_components/notification/Notification';

async function NotificationPage() {
  const notifications = await getNotificationList();

  if (!notifications.success)
    return (
      <div className="w-full h-full flex flex-col break-keep justify-center items-center text-3xl text-center text-main">
        알림을 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
      </div>
    );
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
