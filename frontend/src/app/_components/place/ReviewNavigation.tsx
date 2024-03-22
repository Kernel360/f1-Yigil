'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';

function isCurrent(id: number, tab: string, pathname: string) {
  if (tab === '') return pathname === `/place/${id}`;

  return pathname.startsWith(`/place/${id}/${tab}`);
}

export default function ReviewsNavigation({ id }: { id: number }) {
  const pathname = usePathname();

  return (
    <nav className="px-4 py-2 flex flex-col gap-2">
      <h2 className="text-xl font-medium" id="reviews">
        리뷰
      </h2>
      <div className="flex gap-4">
        <Link
          className={`text-lg font-medium ${
            isCurrent(id, '', pathname)
              ? 'border-b-4 border-black'
              : 'text-gray-500'
          }`}
          href={`/place/${id}#reviews`}
        >
          장소
        </Link>
        <Link
          className={`text-lg font-medium ${
            isCurrent(id, 'courses', pathname)
              ? 'border-b-4 border-black'
              : 'text-gray-500'
          }`}
          href={`/place/${id}/courses`}
        >
          코스
        </Link>
      </div>
    </nav>
  );
}
