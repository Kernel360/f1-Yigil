import Link from 'next/link';

import SearchIcon from '/public/icons/search.svg';
import BellIcon from '/public/icons/bell.svg';

export default function HeaderIcons() {
  return (
    <div className="absolute right-4 flex items-center gap-x-2">
      <Link href="/search">
        <SearchIcon />
      </Link>
      <Link href="/notification">
        <BellIcon />
      </Link>
    </div>
  );
}
