import React, { useEffect, useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';
import { TNotification } from '@/types/notificationResponse';

export default function NotificationItem({
  notification_id,
  sender_profile_image_url,
  sender_id,
  message,
  create_date,
  read,
}: TNotification['notifications'][0]) {
  const [isRead, setIsRead] = useState(read);
  useEffect(() => {
    setIsRead(true);
  }, []);

  const clickToRead = () => {
    // post
  };
  return (
    /** 유저 상세 페이지가 만들어질 경우 Link로 유저 상세페이지로 이동  */
    <div className="flex items-center gap-x-4" onClick={clickToRead}>
      <RoundProfile img={sender_profile_image_url} size={48} height="h-12" />
      <div className={`${isRead && 'text-gray-300'}`}>{message}</div>
    </div>
  );
}
