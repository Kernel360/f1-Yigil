'use client';

import { useEffect } from 'react';

import { ZodError } from 'zod';

export default function ReviewsErrorPage(
  error: Error & ZodError & { digest?: string },
) {
  useEffect(() => {
    console.error(error.issues);
  }, [error]);

  return <h2>Error!</h2>;
}
