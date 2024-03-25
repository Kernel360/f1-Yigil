import { TMyPageFollower } from '@/types/myPageResponse';
import React, { useState } from 'react';
import Dialog from '../../ui/dialog/Dialog';
import RoundProfile from '../../ui/profile/RoundProfile';
import { postFollow } from '../hooks/followActions';
import ToastMsg from '../../ui/toast/ToastMsg';

const MyPageFollowerItem = ({
  member_id,
  nickname,
  profile_image_url,
  following,
}: TMyPageFollower) => {
  const [isFollowing, setIsFollowing] = useState(following);
  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const [errorText, setErrorText] = useState('');

  const onClickFollowingBtn = async () => {
    const result = await postFollow(member_id, isFollowing);
    if (result.status === 'failed') {
      setErrorText(result.message);
      return;
    }
    setIsFollowing(!isFollowing);
  };
  const closeModal = () => {
    setIsDialogOpened(false);
  };

  const deleteFollwer = async (memberId: number) => {
    const result = await postFollow(member_id, isFollowing);
    if (result.status === 'failed') {
      setErrorText(result.message);
      setIsDialogOpened(false);
      setTimeout(() => {
        setErrorText('');
      }, 1000);
      return;
    } else {
      setIsFollowing(!isFollowing);
      setIsDialogOpened(false);
    }
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
          loadingText="삭제중 입니다."
          handleConfirm={async () => deleteFollwer(member_id)}
        />
      )}
      {errorText && <ToastMsg title={errorText} timer={1000} id={Date.now()} />}
    </div>
  );
};

export default MyPageFollowerItem;
