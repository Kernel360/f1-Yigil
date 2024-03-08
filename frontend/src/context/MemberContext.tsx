'use client';

import { createContext } from 'react';

import { ReactNode } from 'react';
import type { TMyInfo } from '@/types/response';

export type TMemberStatus =
  | { isLoggedIn: 'false' }
  | { member: TMyInfo; isLoggedIn: 'true' };

export const MemberContext = createContext<TMemberStatus>({
  isLoggedIn: 'false',
});

export default function MemberProvider({
  status,
  children,
}: {
  status: TMemberStatus;
  children: ReactNode;
}) {
  return (
    <MemberContext.Provider value={status}>{children}</MemberContext.Provider>
  );
}
