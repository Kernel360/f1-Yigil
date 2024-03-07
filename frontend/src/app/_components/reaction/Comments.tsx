'use client';

import { useContext, useRef, useState } from 'react';
import Comment from './Comment';
import { MemberContext } from '@/context/MemberContext';

import { EventFor } from '@/types/type';
import type { TComment } from '@/types/response';

import PlusIcon from '/public/icons/plus.svg';
import { useRouter } from 'next/navigation';

export default function Comments({ placeId }: { placeId: number }) {
  const { push } = useRouter();

  const textareaRef = useRef<HTMLTextAreaElement>(null);

  const [comments, setComments] = useState<TComment[]>([]);
  const [draft, setDraft] = useState('');

  const memberStatus = useContext(MemberContext);

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

    console.log(`Submit: ${draft}`);
  }

  return (
    <section>
      {comments.map((comment) => (
        <Comment key={comment.id} data={comment} />
      ))}
      <hr />
      <div className="w-full p-2 flex gap-2 items-center">
        <textarea
          className="p-3 resize-none overflow-hidden bg-gray-100 rounded-xl grow"
          ref={textareaRef}
          placeholder="댓글을 입력하세요."
          onChange={handleChange}
          value={draft}
        />
        <button
          className="p-2 rounded-full bg-black flex justify-center items-center grow-0"
          onClick={handleSubmit}
        >
          <PlusIcon className="w-6 h-6 stroke-white" />
        </button>
      </div>
    </section>
  );
}
