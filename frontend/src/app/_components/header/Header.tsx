import React from 'react';
import HeaderClient from './HeaderClient';
import { Session } from 'next-auth';

export default function Header({ session }: { session: Session | null }) {
  return (
    <div className="w-full py-4 flex justify-between items-center sticky top-0 bg-main z-10">
      <HeaderClient session={session} />
    </div>
  );
}
