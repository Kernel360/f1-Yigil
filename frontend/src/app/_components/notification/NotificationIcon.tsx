'use client';
import React, { useContext, useEffect, useState } from 'react';
import Bell from '/public/icons/bell.svg';
import { useRouter } from 'next/navigation';
import ToastMsg from '../ui/toast/ToastMsg';
import { MemberContext } from '@/context/MemberContext';

const BASE_URL =
  process.env.ENVIRONMENT === 'production'
    ? process.env.NEXT_PUBLIC_BASE_URL
    : process.env.NEXT_PUBLIC_DEV_BASE_URL;

export default function NotificationIcon() {
  const memberStatus = useContext(MemberContext);
  const [newData, setNewData] = useState('');
  const [errorText, setErrorText] = useState('');

  const user = memberStatus.isLoggedIn === 'true' ? memberStatus.member : null;

  useEffect(() => {
    if (user) {
      const eventSource = new EventSource(
        `${BASE_URL}/v1/notifications/stream/${user?.member_id}`,
      );

      eventSource.addEventListener('notification', (e) => {
        setNewData(e.data);
      });

      eventSource.addEventListener('error', (e) => {
        console.error('EventSource 에러 발생:', e);
        setErrorText('실시간 알림을 위한 서버와의 연결이 끊어졌습니다.');
        setTimeout(() => {
          setErrorText('');
        }, 2000);
        eventSource.close();
      });

      return () => eventSource.close();
    }
  }, [user]);

  const { push } = useRouter();
  return (
    <div className="relative flex items-center max-w-[430px]">
      {newData && (
        <div className="p-[6px] rounded-full bg-red-500 absolute top-[2px] right-[6px]"></div>
      )}
      <button
        onClick={() => {
          push('/notification');
          setNewData('');
        }}
      >
        <Bell className="w-10 h-10" />
      </button>
      <div className="text-base">
        {errorText && (
          <ToastMsg title={errorText} timer={1000} id={Date.now()} />
        )}
        {newData && <ToastMsg title={newData} timer={2000} id={Date.now()} />}
      </div>
    </div>
  );
}
