'use client';

import ErrorComponent from '@/app/_components/ErrorComponent';
import { checkBatchimEnding } from '@/utils';
import { useEffect } from 'react';

export default function Error({
  error,
  reset,
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) {
  useEffect(() => {
    console.error(error);
  }, [error]);

  return (
    <ErrorComponent
      title={`유저${checkBatchimEnding('유저') ? '을' : '를'}`}
      reset={reset}
    />
  );
}
