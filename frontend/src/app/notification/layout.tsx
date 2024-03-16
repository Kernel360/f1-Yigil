import React from 'react';
import Header from '../_components/header/Header';
import BackButton from '../_components/place/BackButton';

export default function Notificationlayout() {
  return (
    <>
      <Header />
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">알림</span>
      </nav>
    </>
  );
}
