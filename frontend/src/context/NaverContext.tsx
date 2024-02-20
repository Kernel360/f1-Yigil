'use client';

import React, { ReactNode } from 'react';
import { NavermapsProvider } from 'react-naver-maps';

export default function NaverContext({ children }: { children: ReactNode }) {
  return (
    <NavermapsProvider
      ncpClientId={process.env.NEXT_PUBLIC_NAVER_MAPS_CLIENT_ID}
      submodules={['geocoder']}
    >
      {children}
    </NavermapsProvider>
  );
}
