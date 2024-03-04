import { TMyPageFollow } from '@/types/myPageResponse';
import React, { useState } from 'react';
import RoundProfile from '../../ui/profile/RoundProfile';
import { addFollow, unFollow } from '../hooks/followActions';

interface TMyPageFollowingItem extends TMyPageFollow {
  idx: number;
}

export default function MyPageFollowingItem({
  idx,
  member_id,
  nickname,
  profile_image_url,
}: TMyPageFollowingItem) {
  const [isFollowing, setIsFollowing] = useState(true);

  const onClickFollowingBtn = async () => {
    if (isFollowing) {
      unFollow(member_id);
    } else {
      addFollow(member_id);
    }
    setIsFollowing(!isFollowing);
  };
  return (
    <div className="flex justify-center items-center py-3">
      <RoundProfile img={profile_image_url} size={48} />
      <div className="grow ml-4 text-gray-900">{nickname}</div>
      <button
        className={`${
          isFollowing ? 'bg-gray-200 text-gray-500' : 'bg-blue-500 text-white'
        } rounded-md px-4 py-2 leading-5`}
        onClick={onClickFollowingBtn}
      >
        팔로잉
      </button>
    </div>
  );
}
