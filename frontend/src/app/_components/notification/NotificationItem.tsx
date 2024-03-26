import React, { useEffect, useState } from 'react';
import RoundProfile from '../ui/profile/RoundProfile';

export default function NotificationItem({
  message,
  create_date,
  read,
}: {
  message: string;
  create_date: string;
  read: boolean;
}) {
  const [isRead, setIsRead] = useState(read);
  useEffect(() => {
    setIsRead(true);
  }, []);
  return (
    <div className="flex items-center gap-x-4">
      <RoundProfile size={48} />
      <div className={`${isRead && 'text-gray-300'}`}>{message}</div>
    </div>
  );
}
