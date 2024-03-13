'use client';
import React from 'react';
import BackButton from '../place/BackButton';
import { getBaseUrl } from '@/app/utilActions';

export default function Notification() {
  const eventSource = new EventSource(`http://3.34.236.45:8080/api/v1/stream`);

  return (
    <div>
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">알림</span>
      </nav>
    </div>
  );
}
