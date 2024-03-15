'use client';
import React, { useEffect } from 'react';
import BackButton from '../place/BackButton';
import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';
import { getNotifications } from './notificationActions';

export default function Notification() {
  useEffect(() => {
    const eventSource = new EventSource(
      `http://localhost:3000/endpoints/api/notification`,
      // `http://3.34.236.45:8080/api/v1/notifications/stream`,
      {
        withCredentials: true,
      },
    );
    // console.log('eve', eventSource);
    // console.log('ready', eventSource);

    eventSource.onopen = (e) => {
      console.log(e);
      console.log('opened');
    };

    eventSource.onmessage = (e) => {
      console.log(e.data);
    };

    return () => eventSource.close();
  }, []);

  // useEffect(() => {
  //   getAlarms();
  // }, []);

  // const getAlarms = async () => {
  //   const res = await getNotifications();
  //   console.log(res);
  // };

  // eventSource.addEventListener('open', () => {
  //   console.log('open');
  // });
  // eventSource.onerror = () => {
  //   eventSource.close();
  // };

  return (
    <div>
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">알림</span>
      </nav>
    </div>
  );
}
