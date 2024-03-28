'use client';

import { useState } from 'react';

import RoundProfile from '../ui/profile/RoundProfile';
import MoreButton from './MoreButton';

import ToastMsg from '../ui/toast/ToastMsg';
import Spinner from '../ui/Spinner';

import type { TComment } from '@/types/response';
import { deleteComment, modifyComment } from './action';
import CommentContent from './CommentContent';

export default function Comment({
  data,
  travelId,
}: {
  data: TComment;
  travelId: number;
}) {
  const {
    id: commentId,
    member_image_url,
    member_nickname,
    created_at,
    content,
    member_id,
  } = data;

  const [isLoading, setIsLoading] = useState(false);
  const [isDeleted, setIsDeleted] = useState(content === '삭제된 댓글입니다.');
  const [error, setError] = useState('');

  const [draft, setDraft] = useState(content);

  const [editOrDelete, setEditOrDelete] = useState<'기본' | '삭제' | '수정'>(
    '기본',
  );

  async function deleteThisComment() {
    setIsLoading(true);

    const result = await deleteComment(travelId, commentId);

    if (result.status === 'failed') {
      setError(result.message);
      setTimeout(() => setError(''), 2000);
      setIsLoading(false);
      return;
    }

    setIsDeleted(true);
    setIsLoading(false);
  }

  async function modifyThisComment() {
    if (draft.trim() === '') {
      return;
    }

    setIsLoading(true);

    const result = await modifyComment(travelId, commentId, draft);

    if (result.status === 'failed') {
      setError(result.message);
      setDraft(content);
      setTimeout(() => setError(''), 2000);
      setIsLoading(false);
      return;
    }

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
        <CommentContent
          isDeleted={isDeleted}
          isLoading={isLoading}
          isEditing={editOrDelete === '수정'}
          content={draft}
          setContent={setDraft}
        />
      </div>
      <div className="relative px-4 py-2 flex gap-4 justify-end">
        {!isDeleted && (
          <MoreButton
            ownerId={member_id}
            editOrDelete={editOrDelete}
            setEditOrDelete={setEditOrDelete}
            deleteComment={deleteThisComment}
            modifyComment={modifyThisComment}
          />
        )}
      </div>
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </section>
  );
}
