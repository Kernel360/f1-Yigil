'use client';

import { useRef, useState } from 'react';
import Comment from './Comment';

import { EventFor } from '@/types/type';
import type { TComment } from '@/types/response';

import PlusIcon from '/public/icons/plus.svg';

export default function Comments({ placeId }: { placeId: number }) {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const [comments, setComments] = useState<TComment[]>([]);
  const [draft, setDraft] = useState('');

  function handleChange(event: EventFor<'textarea', 'onChange'>) {
    setDraft(event.target.value);
    textareaRef.current?.style.setProperty('height', 'inherit');
    textareaRef.current?.style.setProperty(
      'height',
      `${textareaRef.current.scrollHeight}px`,
    );
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
          onFocus={() => console.log(textareaRef.current?.scrollHeight)}
        />
        <button className="p-2 rounded-full bg-black flex justify-center items-center grow-0">
          <PlusIcon className="w-6 h-6 stroke-white" />
        </button>
      </div>
    </section>
  );
}
