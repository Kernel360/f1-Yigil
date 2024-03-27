'use client';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { myPageFavoriteTabs } from '../constants';
import { checkPath } from './MyPageRoutes';

export default function MyPageFavoriteRoutes() {
  const path = usePathname();

  return (
    <div className="w-[50%] flex justify-between ">
      {myPageFavoriteTabs.map(({ label, href }) => (
        <Link
          key={href}
          href={href}
          className={`text-xl leading-6 w-full text-center ${
            checkPath(path, href, 2)
              ? 'text-gray-700 font-semibold'
              : 'text-gray-300 font-normal'
          }
      `}
        >
          {label}
        </Link>
      ))}
    </div>
  );
}
