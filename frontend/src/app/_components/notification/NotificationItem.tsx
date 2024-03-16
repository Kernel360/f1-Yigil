import React from 'react';
import RoundProfile from '../ui/profile/RoundProfile';

export default function NotificationItem({
  message,
  create_date,
}: // read,
{
  message: string;
  create_date: string;
  // read: boolean;
}) {
  return (
    <div className="flex items-center gap-x-4">
      <RoundProfile size={48} />
      <div>{message}</div>
    </div>
  );
}
