import { TMyPageFollow, TMyPageFollower } from '@/types/myPageResponse';
import React, { useState } from 'react';
import Dialog from '../../ui/dialog/Dialog';
import RoundProfile from '../../ui/profile/RoundProfile';
import { postFollow } from '../hooks/followActions';
import ToastMsg from '../../ui/toast/ToastMsg';

interface TMyPageFollowProps extends TMyPageFollow {
  following?: boolean;
  action: 'followings' | 'followers';
}

const MyPageFollowItem = ({
  member_id,
  nickname,
  profile_image_url,
  following,
  action,
}: TMyPageFollowProps) => {
  const [isFollowing, setIsFollowing] = useState(
    action === 'followings' ? true : (following as boolean),
  );

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
    <div className="flex justify-center items-center my-4">
      <RoundProfile img={profile_image_url} size={48} height="h-12" />
      <div className="ml-4 text-gray-900  overflow-hidden text-ellipsis whitespace-nowrap break-words">
        {nickname}
      </div>
      <div className="grow"></div>

      {action === 'followers' ? (
        <button
          className={`ml-4 text-start shrink-0 ${
            isFollowing ? 'text-gray-500' : 'text-blue-500'
          }`}
          onClick={onClickFollowingBtn}
        >
          {isFollowing ? '팔로잉' : '팔로우'}
        </button>
      ) : (
        <button
          className={`${
            isFollowing ? 'bg-gray-200 text-gray-500' : 'bg-blue-500 text-white'
          } rounded-md px-4 py-2 leading-5`}
          onClick={onClickFollowingBtn}
        >
          팔로잉
        </button>
      )}

      {errorText && <ToastMsg title={errorText} timer={1000} id={Date.now()} />}
    </div>
  );
};

export default MyPageFollowItem;
