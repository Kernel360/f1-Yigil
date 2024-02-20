import Link from 'next/link';

import SearchIcon from '/public/icons/search.svg';
import XMarkIcon from '/public/icons/x-mark.svg';

export default function SearchItem({
  item,
  href,
  deleteResult,
  erasable,
}: {
  item: string;
  href: string;
  deleteResult: (result: string) => void;
  erasable?: boolean;
}) {
  return (
    <div className="flex justify-between">
      <Link className="flex py-3 grow items-center" href={href}>
        <span className="flex gap-1 items-center">
          <SearchIcon className="w-6 h-6 mr-4" />
          {item}
        </span>
      </Link>
      {erasable && (
        <button className="p-3" onClick={() => deleteResult(item)}>
          <XMarkIcon className="w-4 h-4" />
        </button>
      )}
    </div>
  );
}
