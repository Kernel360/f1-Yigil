'use client';

import { useContext, useState } from 'react';
import { MemberContext } from '@/context/MemberContext';

import PopOverIcon from '../ui/popover/PopOverItem';

import type { TPopOverData } from '../ui/popover/types';

import MoreIcon from '/public/icons/more.svg';
import AlertIcon from '/public/icons/info.svg';
import TrashIcon from '/public/icons/trash.svg';
import EditIcon from '/public/icons/edit.svg';

export default function MoreButton({
  ownerId,
}: {
  ownerId: number;
  deleteComment: () => Promise<void>;
}) {
  const memberStatus = useContext(MemberContext);
  const [isOpen, setIsOpen] = useState(false);

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
      { label: '수정하기', icon: <EditIcon className="w-6 h-6 fill-none" /> },
      { label: '삭제하기', icon: <TrashIcon className="w-6 h-6" /> },
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
    </button>
  );
}
