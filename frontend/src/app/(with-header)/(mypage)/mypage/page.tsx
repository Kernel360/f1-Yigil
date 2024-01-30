import MyPageContent from '@/app/_components/mypage/MyPageContent';
import MyPageInfo from '@/app/_components/mypage/MyPageInfo';
import React from 'react';
/**
 * TODO: FAB 버튼 추가
 */
export default function MyPage() {
  return (
    <div>
      <MyPageInfo />
      <div className="">
        <MyPageContent />
      </div>
    </div>
  );
}
