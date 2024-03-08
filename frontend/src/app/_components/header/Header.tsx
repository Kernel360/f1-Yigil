import React from 'react';
import HeaderClient from './HeaderClient';

export default function Header() {
  return (
    <div className="w-full py-4 flex justify-between items-center sticky top-0 bg-main z-10">
      <HeaderClient />
    </div>
  );
}
