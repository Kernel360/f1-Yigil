'use client';

import { useContext, useState } from 'react';
import { ReportContext } from '@/context/ReportContext';

import RoundProfile from '../ui/profile/RoundProfile';
import MoreButton from './MoreButton';

import ToastMsg from '../ui/toast/ToastMsg';
import CommentContent from './CommentContent';

import type { TComment } from '@/types/response';
import { deleteComment, modifyComment } from './action';
import { postReport } from '@/app/(with-header)/place/[id]/@reviews/action';

export default function Comment({
  data,
  travelId,
}: {
  data: TComment;
  travelId: number;
}) {
  const reportTypes = useContext(ReportContext);
  const {
    id: commentId,
    deleted,
    member_image_url,
    member_nickname,
    created_at,
    content,
    member_id,
  } = data;

  const [isLoading, setIsLoading] = useState(false);
  const [isDeleted, setIsDeleted] = useState(deleted);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const [draft, setDraft] = useState(content);

  const [editOrDelete, setEditOrDelete] = useState<'기본' | '삭제' | '수정'>(
    '기본',
  );

  const [selectedValue, setSelectedValue] = useState(
    reportTypes ? reportTypes[0].id : 0,
  );
  const [isReportDialogOpen, setIsReportDialogOpen] = useState(false);

  async function reportThisComment() {
    const result = await postReport(selectedValue, commentId, 'comment');

    if (result.status === 'failed') {
      setError(result.message);
      setTimeout(() => setError(''), 2000);
      return;
    }

    setMessage('신고가 정상적으로 접수되었습니다!');
    setTimeout(() => setError(''), 2000);
  }

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
            reportTypes={reportTypes}
            editOrDelete={editOrDelete}
            setEditOrDelete={setEditOrDelete}
            isReportDialogOpen={isReportDialogOpen}
            setIsReportDialogOpen={setIsReportDialogOpen}
            selectedValue={selectedValue}
            setSelectedValue={setSelectedValue}
            deleteComment={deleteThisComment}
            modifyComment={modifyThisComment}
            reportComment={reportThisComment}
          />
        )}
      </div>
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
      {message && <ToastMsg id={Date.now()} title={message} timer={2000} />}
    </section>
  );
}
