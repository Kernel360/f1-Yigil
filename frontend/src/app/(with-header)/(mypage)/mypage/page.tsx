import MyPageContent from '@/app/_components/mypage/MyPageContent';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import React from 'react';

export default function MyPage() {
  return (
    <div className="">
      <MyPageInfo />
      <div className="">
        <MyPageContent />
      </div>
    </div>
  );
}
