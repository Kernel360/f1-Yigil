'use client';

import { useContext, useState } from 'react';
import { MemberContext } from '@/context/MemberContext';

import PopOverIcon from '../ui/popover/PopOverItem';

import { Dispatch, SetStateAction } from 'react';
import type { TPopOverData } from '../ui/popover/types';

import MoreIcon from '/public/icons/more.svg';
import AlertIcon from '/public/icons/info.svg';
import TrashIcon from '/public/icons/trash.svg';
import EditIcon from '/public/icons/edit.svg';
import Dialog from '../ui/dialog/Dialog';

export default function MoreButton({
  ownerId,
  editOrDelete,
  setEditOrDelete,
  deleteComment,
  modifyComment,
}: {
  ownerId: number;
  editOrDelete: '기본' | '삭제' | '수정';
  setEditOrDelete: Dispatch<SetStateAction<'기본' | '삭제' | '수정'>>;
  deleteComment: () => Promise<void>;
  modifyComment: () => Promise<void>;
}) {
  const memberStatus = useContext(MemberContext);
  const [isOpen, setIsOpen] = useState(false);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  if (memberStatus.isLoggedIn === 'false') {
    return null;
  }

  const popOverData: TPopOverData[] = [
    {
      label: '신고하기',
      icon: <AlertIcon className="w-6 h-6 fill-gray-500" />,
    },
  ];

  if (memberStatus.member.member_id === ownerId) {
    popOverData.push(
      {
        label: '수정하기',
        icon: <EditIcon className="w-6 h-6 fill-none" />,
        onClick: () => {
          setEditOrDelete('수정');
        },
      },
      {
        label: '삭제하기',
        icon: <TrashIcon className="w-6 h-6" />,
        onClick: () => {
          setEditOrDelete('삭제');
          setIsDialogOpen(true);
        },
      },
    );
  }

  async function handleDialogConfirm() {
    if (editOrDelete === '기본') {
      setIsDialogOpen(false);
      return;
    }

    if (editOrDelete === '수정') {
      setIsDialogOpen(false);
      await modifyComment();
      setEditOrDelete('기본');
      return;
    }

    setIsDialogOpen(false);
    await deleteComment();
    setEditOrDelete('기본');
  }

  if (editOrDelete === '수정') {
    return (
      <button className="text-gray-500" onClick={() => setIsDialogOpen(true)}>
        완료
        {isDialogOpen && (
          <Dialog
            text={`댓글을 ${editOrDelete}하시겠나요?`}
            closeModal={() => setIsDialogOpen(false)}
            handleConfirm={handleDialogConfirm}
          />
        )}
      </button>
    );
  }

  return (
    <button onClick={() => setIsOpen(!isOpen)}>
      <MoreIcon className="w-6 h-6" />
      {isOpen && (
        <div className="p-2 absolute bottom-full bg-white rounded-xl border right-4 flex flex-col">
          {popOverData.map((data) => (
            <PopOverIcon
              key={data.label}
              data={data}
              closeModal={() => setIsOpen(false)}
            />
          ))}
        </div>
      )}
      {isDialogOpen && (
        <Dialog
          text={`댓글을 ${editOrDelete}하시겠나요?`}
          closeModal={() => setIsDialogOpen(false)}
          handleConfirm={handleDialogConfirm}
        />
      )}
    </button>
  );
}
