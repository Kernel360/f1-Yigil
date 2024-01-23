import React from 'react';
import MyPageContent from '../../_components/mypage/MyPageContent';
import MyPageInfo from '../../_components/mypage/MyPageInfo';

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
