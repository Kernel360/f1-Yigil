'use client';

import { useContext, useEffect, useRef, useState } from 'react';
import { useRouter } from 'next/navigation';
import { getComments, postComment } from './action';

import { MemberContext } from '@/context/MemberContext';

import Spinner from '../ui/Spinner';
import ToastMsg from '../ui/toast/ToastMsg';
import Comment from './Comment';

import type { EventFor } from '@/types/type';
import type { TComment } from '@/types/response';

import PlusIcon from '/public/icons/plus.svg';

export default function Comments({ parentId }: { parentId: number }) {
  const { push } = useRouter();
  const { isLoggedIn } = useContext(MemberContext);

  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const [draft, setDraft] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [hasNext, setHasNext] = useState(false);
  const [comments, setComments] = useState<TComment[]>([]);
  const [error, setError] = useState('');

  const memberStatus = useContext(MemberContext);

  useEffect(() => {
    async function getCommentsFromBackend(travelId: number) {
      setIsLoading(true);

      const commentsFromBackend = await getComments(travelId);

      if (commentsFromBackend.status === 'failed') {
        setError(commentsFromBackend.message);
        setTimeout(() => setError(''), 2000);
        setIsLoading(false);
        return;
      }

      setComments(commentsFromBackend.data.content);
      setHasNext(commentsFromBackend.data.has_next);
      setIsLoading(false);
    }

    getCommentsFromBackend(parentId);
  }, [parentId]);

  function handleChange(event: EventFor<'textarea', 'onChange'>) {
    setDraft(event.target.value);
    textareaRef.current?.style.setProperty('height', 'inherit');
    textareaRef.current?.style.setProperty(
      'height',
      `${textareaRef.current.scrollHeight}px`,
    );
  }

  async function handleSubmit() {
    if (memberStatus.isLoggedIn === 'false') {
      push('/login');
      return;
    }

    if (draft.length === 0) {
      console.log('Empty');
      return;
    }

    setIsLoading(true);

    const writeResult = await postComment(parentId, draft);

    if (writeResult.status === 'failed') {
      setError(writeResult.message);
      setIsLoading(false);
      setTimeout(() => setError(''), 2000);
      return;
    }

    const nextCommentsResult = await getComments(parentId);

    if (nextCommentsResult.status === 'failed') {
      setError(nextCommentsResult.message);
      setIsLoading(false);
      setTimeout(() => setError(''), 2000);
      return;
    }

    setComments(nextCommentsResult.data.content);
    setHasNext(nextCommentsResult.data.has_next);
    setDraft('');
    setIsLoading(false);
  }

  async function handleMore() {
    const nextPage = currentPage + 1;

    setIsLoading(true);

    const nextCommentsResult = await getComments(parentId, nextPage);

    if (nextCommentsResult.status === 'failed') {
      setError(nextCommentsResult.message);
      setIsLoading(false);
      setTimeout(() => setError(''), 2000);
      return;
    }

    setComments([...comments, ...nextCommentsResult.data.content]);
    setHasNext(nextCommentsResult.data.has_next);
    setCurrentPage(nextPage);
    setIsLoading(false);
  }

  return (
    <section className="flex flex-col">
      <div className="min-h-24 flex flex-col justify-center items-center">
        {!isLoading && comments.length === 0 ? (
          <span className="self-center">댓글이 없습니다.</span>
        ) : (
          <div className="flex w-full flex-col">
            {comments.map((comment) => (
              <Comment key={comment.id} data={comment} travelId={parentId} />
            ))}
          </div>
        )}
        {isLoading && (
          <div className="py-4">
            <Spinner />
          </div>
        )}
      </div>
      {hasNext && (
        <div className="flex justify-center">
          <button
            className="py-4 w-full bg-gray-200 text-gray-500 font-medium"
            onClick={handleMore}
          >
            댓글 더보기
          </button>
        </div>
      )}
      <hr />
      <div className="w-full p-2 flex gap-2 items-center">
        <textarea
          className="p-3 resize-none overflow-hidden bg-gray-100 rounded-xl grow"
          rows={1}
          ref={textareaRef}
          disabled={isLoggedIn === 'false' || isLoading}
          placeholder={
            isLoggedIn === 'true'
              ? '댓글을 입력하세요.'
              : '로그인 후 댓글을 입력할 수 있습니다!'
          }
          onChange={handleChange}
          value={draft}
        />
        <button
          className="p-2 rounded-full bg-black flex justify-center items-center grow-0"
          onClick={async () => await handleSubmit()}
        >
          <PlusIcon className="w-6 h-6 stroke-white" />
        </button>
      </div>
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </section>
  );
}
