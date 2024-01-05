'use client';

import { useEffect } from 'react';

export default function MSWComponent() {
  useEffect(() => {
    if (process.env.NODE_ENV !== 'production') {
      return;
    } else if (typeof window !== 'undefined') {
      require('@/mocks/browser');
    }
  }, []);

  return null;
}
