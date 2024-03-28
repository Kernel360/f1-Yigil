'use client';

import { useContext, useState } from 'react';
import { MemberContext } from '@/context/MemberContext';

import PopOverIcon from '../ui/popover/PopOverItem';
import ToastMsg from '../ui/toast/ToastMsg';
import ReportDialog from '../ui/dialog/ReportDialog';

import type { Dispatch, SetStateAction } from 'react';
import type { TReportType } from '@/types/response';
import type { TPopOverData } from '../ui/popover/types';

import MoreIcon from '/public/icons/more.svg';
import AlertIcon from '/public/icons/info.svg';
import TrashIcon from '/public/icons/trash.svg';
import EditIcon from '/public/icons/edit.svg';
import Dialog from '../ui/dialog/Dialog';

export default function MoreButton({
  ownerId,
  reportTypes,
  editOrDelete,
  setEditOrDelete,
  isReportDialogOpen,
  setIsReportDialogOpen,
  selectedValue,
  setSelectedValue,
  deleteComment,
  modifyComment,
  reportComment,
}: {
  ownerId: number;
  reportTypes?: TReportType[];
  editOrDelete: '기본' | '삭제' | '수정';
  setEditOrDelete: Dispatch<SetStateAction<'기본' | '삭제' | '수정'>>;
  isReportDialogOpen: boolean;
  setIsReportDialogOpen: Dispatch<SetStateAction<boolean>>;
  selectedValue: number;
  setSelectedValue: Dispatch<SetStateAction<number>>;
  deleteComment: () => Promise<void>;
  modifyComment: () => Promise<void>;
  reportComment: () => Promise<void>;
}) {
  const memberStatus = useContext(MemberContext);
  const [isOpen, setIsOpen] = useState(false);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const [error, setError] = useState('');

  if (memberStatus.isLoggedIn === 'false') {
    return null;
  }

  const popOverData: TPopOverData[] = [
    {
      label: '신고하기',
      icon: <AlertIcon className="w-6 h-6 fill-gray-500" />,
      onClick: () => {
        if (reportTypes === undefined) {
          setError('신고는 로그인 후 가능합니다!');
          setTimeout(() => setError(''), 2000);
          return;
        }

        setIsReportDialogOpen(true);
      },
    },
  ];

  if (memberStatus.member.member_id === ownerId) {
    popOverData.push(
      {
        label: '수정하기',
        icon: <EditIcon className="w-6 h-6 fill-none stroke-black" />,
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

  async function handleReport() {
    setIsReportDialogOpen(false);
    await reportComment();
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
      {isReportDialogOpen && (
        <ReportDialog
          reportTypes={reportTypes}
          reportTypeId={selectedValue}
          setReportTypeId={setSelectedValue}
          handleConfirm={handleReport}
          closeModal={() => setIsReportDialogOpen(false)}
        />
      )}
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </button>
  );
}
