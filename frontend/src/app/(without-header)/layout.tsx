import localFont from 'next/font/local';

import MSWComponent from '@/app/_components/MSWComponent';
import Header from '@/app/_components/header/Header';
import AuthContext from '@/context/AuthContext';

import type { ReactNode } from 'react';

import '../globals.css';

const Jalnan = localFont({
  src: '../../../public/fonts/Jalnan2TTF.ttf',
  display: 'swap',
});

export default function WithoutHeaderLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <html lang="ko" className={Jalnan.className}>
      <body>
        <MSWComponent />
        <AuthContext>{children}</AuthContext>
      </body>
    </html>
  );
}
