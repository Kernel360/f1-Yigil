'use client';

import { useContext, useEffect, useRef, useState } from 'react';
import Comment from './Comment';
import { MemberContext } from '@/context/MemberContext';

import { EventFor } from '@/types/type';
import { postResponseSchema, type TComment } from '@/types/response';

import PlusIcon from '/public/icons/plus.svg';
import { useRouter } from 'next/navigation';
import { getComments, postComment } from './action';
import Spinner from '../ui/Spinner';
import ToastMsg from '../ui/toast/ToastMsg';

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

      if (!commentsFromBackend.success) {
        // Error UI
        console.log(commentsFromBackend.error.message);
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

    if (!nextCommentsResult.success) {
      console.log('Read failed');
      console.log(`Error: ${nextCommentsResult.error.message}`);
      setIsLoading(false);
      return;
    }

    setComments(nextCommentsResult.data.content);
    setHasNext(nextCommentsResult.data.has_next);
    setDraft('');
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
              <Comment key={comment.id} data={comment} />
            ))}
          </div>
        )}
        {isLoading && (
          <div className="py-4">
            <Spinner />
          </div>
        )}
      </div>
      <hr />
      <div className="w-full p-2 flex gap-2 items-center">
        <textarea
          className="p-3 resize-none overflow-hidden bg-gray-100 rounded-xl grow"
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
