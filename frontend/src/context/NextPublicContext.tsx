'use client';
import { ReactElement, createContext } from 'react';

export const NextPublicContext = createContext<string>('');

export default function NextPublicProvider({
  children,
  nextBaseUrl,
}: {
  children: ReactElement;
  nextBaseUrl: string;
}) {
  return (
    <NextPublicContext.Provider value={nextBaseUrl}>
      {children}
    </NextPublicContext.Provider>
  );
}
