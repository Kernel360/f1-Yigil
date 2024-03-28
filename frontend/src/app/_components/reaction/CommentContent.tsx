'use client';

import { Dispatch, SetStateAction, useEffect, useRef, useState } from 'react';

import Spinner from '../ui/Spinner';

export default function CommentContent({
  content,
  setContent,
  isLoading,
  isDeleted,
  isEditing,
}: {
  content: string;
  setContent: Dispatch<SetStateAction<string>>;
  isLoading: boolean;
  isDeleted: boolean;
  isEditing: boolean;
}) {
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.focus();
      textareaRef.current.selectionStart = textareaRef.current.value.length;
      textareaRef.current.selectionEnd = textareaRef.current.value.length;
    }
  }, [isEditing]);

  if (isLoading) {
    return (
      <div className="w-full h-full flex justify-center items-center">
        <Spinner />
      </div>
    );
  }

  if (isDeleted) {
    return '삭제된 댓글입니다.';
  }

  if (isEditing) {
    return (
      <textarea
        className="p-2 resize-none w-full h-full rounded-xl border-2 border-violet"
        ref={textareaRef}
        onChange={(e) => setContent(e.target.value)}
        value={content}
      />
    );
  }

  return content;
}
