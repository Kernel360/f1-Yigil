'use client'; // Error components must be Client Components

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
    // Log the error to an error reporting service
    console.error(error);
  }, [error]);

  return (
    <ErrorComponent
      title={`팔로워${checkBatchimEnding('팔로워') ? '을' : '를'}`}
      reset={reset}
    />
  );
}
