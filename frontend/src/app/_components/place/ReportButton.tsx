'use client';

import { useContext, useState } from 'react';
import { ReportContext } from '@/context/ReportContext';
import ReportDialog from '../ui/dialog/ReportDialog';

import ReportIcon from '/public/icons/info.svg';

export default function ReportButton({ parentId }: { parentId: number }) {
  const reportTypes = useContext(ReportContext);
  const [isOpen, setIsOpen] = useState(false);

  async function handleConfirm() {
    setIsOpen(false);
  }

  return (
    <button
      className="text-gray-500 flex gap-2"
      onClick={() => setIsOpen(true)}
    >
      신고
      <ReportIcon className="w-6 h-6 fill-gray-500" />
      {isOpen && (
        <ReportDialog
          reportTypes={reportTypes}
          closeModal={() => setIsOpen(false)}
          handleConfirm={handleConfirm}
        />
      )}
    </button>
  );
}
