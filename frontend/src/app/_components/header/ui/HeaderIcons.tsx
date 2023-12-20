import Image from 'next/image';
import React from 'react';
import SearchIcon from '/public/icons/search.svg';
import BellIcon from '/public/icons/bell.svg';
import Link from 'next/link';

export default function HeaderIcons() {
  return (
    <div className="absolute right-4 flex items-center gap-x-2 ">
      <Link href="/search">
        <Image src={SearchIcon} alt="search-icon" />
      </Link>
      <Link href="/notification">
        <Image src={BellIcon} alt="bell-icon" />
      </Link>
    </div>
  );
}
