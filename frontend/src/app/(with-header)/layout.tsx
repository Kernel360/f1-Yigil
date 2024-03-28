import type { ReactNode } from 'react';
import '../globals.css';
import Header from '../_components/header/Header';
import NextPublicProvider from '@/context/NextPublicContext';

export default async function WithHeaderLayout({
  children,
}: {
  children: ReactNode;
}) {
  const { DEV_BASE_URL, BASE_URL, ENVIRONMENT } = process.env;
  const currentBaseUrl = ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;
  return (
    <NextPublicProvider nextBaseUrl={currentBaseUrl}>
      <section className="w-full h-full flex flex-col">
        <Header />
        {children}
      </section>
    </NextPublicProvider>
  );
}
