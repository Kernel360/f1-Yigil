import { TMyPageFollower } from '@/types/myPageResponse';
import React, { useState } from 'react';
import Dialog from '../../ui/dialog/Dialog';
import RoundProfile from '../../ui/profile/RoundProfile';
import { addFollow, unFollow } from '../hooks/followActions';

const MyPageFollowerItem = ({
  member_id,
  nickname,
  profile_image_url,
  following,
}: TMyPageFollower) => {
  const [isFollowing, setIsFollowing] = useState(following);
  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const onClickFollowingBtn = async () => {
    if (isFollowing) {
      unFollow(member_id);
    } else {
      addFollow(member_id);
    }
    setIsFollowing(!isFollowing);
  };
  const closeModal = () => {
    setIsDialogOpened(false);
  };

  const deleteFollwer = (memberId: number) => {
    // 삭제 로직
  };

  return (
    <div className="flex justify-center items-center my-4">
      <RoundProfile img={profile_image_url} size={48} height="h-12" />
      <div className="ml-4 text-gray-900  overflow-hidden text-ellipsis whitespace-nowrap break-words">
        {nickname}
      </div>
      <button
        className={`ml-4 text-start grow shrink-0 ${
          isFollowing ? 'text-gray-500' : 'text-blue-500'
        }`}
        onClick={onClickFollowingBtn}
      >
        {isFollowing ? '팔로잉' : '팔로우'}
      </button>
      <button
        onClick={() => setIsDialogOpened(true)}
        className={`bg-gray-200 text-gray-500 rounded-md px-6 py-2 leading-5 shrink-0 ml-6`}
      >
        삭제
      </button>
      {isDialogOpened && (
        <Dialog
          closeModal={closeModal}
          text="팔로워를 삭제하시겠나요?"
          handleConfirm={async () => deleteFollwer(member_id)}
        />
      )}
    </div>
  );
};

export default MyPageFollowerItem;
