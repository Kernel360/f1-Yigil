'use client';

import { useState } from 'react';

import RoundProfile from '../ui/profile/RoundProfile';
import MoreButton from './MoreButton';

import ToastMsg from '../ui/toast/ToastMsg';
import Spinner from '../ui/Spinner';

import type { TComment } from '@/types/response';

export default function Comment({ data }: { data: TComment }) {
  const { member_image_url, member_nickname, created_at, content, member_id } =
    data;

  const [isLoading, setIsLoading] = useState(false);
  const [isDeleted, setIsDeleted] = useState(content === '삭제된 댓글입니다.');
  const [error, setError] = useState('');

  async function deleteComment() {
    setIsLoading(true);

    setIsLoading(false);
  }

  return (
    <section className="flex flex-col">
      <div className="p-4 flex justify-between items-center">
        <div className="flex gap-2 items-center">
          <RoundProfile img={member_image_url} size={40} height="h-[40px]" />
          <span>{member_nickname}</span>
        </div>
        <span className="pr-4 text-gray-400 font-medium">
          {created_at.toLocaleDateString()}
        </span>
      </div>
      <div className="px-4 py-2 min-h-28">
        {isLoading ? <Spinner /> : isDeleted ? '삭제된 댓글입니다.' : content}
      </div>
      <div className="relative px-4 flex gap-4 justify-end">
        {!isDeleted && (
          <MoreButton ownerId={member_id} deleteComment={deleteComment} />
        )}
      </div>
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </section>
  );
}
