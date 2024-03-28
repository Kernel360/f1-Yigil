'use client';

import { useContext, useState } from 'react';
import { ReportContext } from '@/context/ReportContext';
import ReportDialog from '../ui/dialog/ReportDialog';

import ReportIcon from '/public/icons/info.svg';
import ToastMsg from '../ui/toast/ToastMsg';
import { postReport } from '@/app/(with-header)/place/[id]/@reviews/action';

export default function ReportButton({ parentId }: { parentId: number }) {
  const reportTypes = useContext(ReportContext);

  const [selectedValue, setSelectedValue] = useState(
    reportTypes ? reportTypes[0].id : 0,
  );
  const [isOpen, setIsOpen] = useState(false);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  function openDialog() {
    if (reportTypes === undefined) {
      setError('신고는 로그인 후 가능합니다!');
      setTimeout(() => setError(''), 2000);
      return;
    }

    setIsOpen(true);
  }

  async function handleConfirm() {
    const result = await postReport(selectedValue, parentId, 'travel');

    if (result.status === 'failed') {
      setError(result.message);
      setTimeout(() => setError(''), 2000);
      setIsOpen(false);
      return;
    }

    setMessage('신고가 정상적으로 접수되었습니다!');
    setTimeout(() => setMessage(''), 2000);
    setIsOpen(false);
  }

  return (
    <button className="text-gray-500 flex gap-2" onClick={openDialog}>
      신고
      <ReportIcon className="w-6 h-6 fill-gray-500" />
      {isOpen && (
        <ReportDialog
          reportTypes={reportTypes}
          closeModal={() => setIsOpen(false)}
          handleConfirm={handleConfirm}
          reportTypeId={selectedValue}
          setReportTypeId={setSelectedValue}
        />
      )}
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
      {message && <ToastMsg id={Date.now()} title={message} timer={2000} />}
    </button>
  );
}
