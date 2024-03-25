import { TMyPageFollow } from '@/types/myPageResponse';
import React, { useState } from 'react';
import RoundProfile from '../../ui/profile/RoundProfile';
import { postFollow } from '../hooks/followActions';
import ToastMsg from '../../ui/toast/ToastMsg';

export default function MyPageFollowingItem({
  member_id,
  nickname,
  profile_image_url,
}: TMyPageFollow) {
  const [isFollowing, setIsFollowing] = useState(true);
  const [errorText, setErrorText] = useState('');

  const onClickFollowingBtn = async () => {
    setIsFollowing(!isFollowing);
    const result = await postFollow(member_id, isFollowing);
    if (result.status === 'failed') {
      setErrorText(result.message);
      setTimeout(() => {
        setErrorText('');
      }, 1000);
      setIsFollowing(!isFollowing);
      return;
    }
  };
  return (
    <div className="flex justify-center items-center py-3">
      <RoundProfile img={profile_image_url} size={48} height="h-12" />
      <div className="grow ml-4 text-gray-900">{nickname}</div>
      <button
        className={`${
          isFollowing ? 'bg-gray-200 text-gray-500' : 'bg-blue-500 text-white'
        } rounded-md px-4 py-2 leading-5`}
        onClick={onClickFollowingBtn}
      >
        팔로잉
      </button>
      {errorText && <ToastMsg title={errorText} timer={1000} id={Date.now()} />}
    </div>
  );
}
