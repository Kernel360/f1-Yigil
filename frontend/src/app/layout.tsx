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
  metadataBase: new URL('https://yigil.co.kr'),
  title: '이길로그',
  icons: {
    icon: [{ url: '/logo/favicon.svg', href: '/logo/favicon.svg' }],
  },
  description: '지도 기반 장소 기록·공유 서비스',
  openGraph: {
    title: '이길로그 홈페이지',
    description: '지도 기반 장소 기록·공유 서비스',
    images: { url: '/logo/og-logo.png', alt: '이길로그 로고 이미지' },
    type: 'website',
    siteName: '이길로그',
    locale: 'ko-KR',
  },
  twitter: {
    title: '이길로그 홈페이지',
    description: '지도 기반 장소 기록·공유 서비스',
    images: { url: '/logo/og-logo.png', alt: '이길로그 로고 이미지' },
  },
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
