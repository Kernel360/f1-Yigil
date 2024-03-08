'use client';

import React, { ReactNode } from 'react';
import { NavermapsProvider } from 'react-naver-maps';

export default function NaverContext({
  children,
  ncpClientId,
}: {
  children: ReactNode;
  ncpClientId: string;
}) {
  return (
    <NavermapsProvider ncpClientId={ncpClientId} submodules={['geocoder']}>
      {children}
    </NavermapsProvider>
  );
}
