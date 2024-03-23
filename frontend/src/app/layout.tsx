export const dynamic = 'force-dynamic';

import localFont from 'next/font/local';
import NaverContext from '@/context/NaverContext';

import './globals.css';

import type { ReactNode } from 'react';
import type { Metadata } from 'next';

const Pretendard = localFont({
  src: '../../public/fonts/PretendardVariable.woff2',
  display: 'swap',
});

export const metadata: Metadata = {
  title: '이길로그',

  icons: {
    icon: [{ url: '/logo/favicon.svg', href: '/logo/favicon.svg' }],
  },

  description: '지도 기반 장소 기록·공유 서비스',
};

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="ko" className={Pretendard.className}>
      <body className="max-w-[430px] mx-auto">
        <div id="modal"></div>
        <NaverContext ncpClientId={process.env.NAVER_MAPS_CLIENT_ID}>
          {children}
        </NaverContext>
      </body>
    </html>
  );
}
