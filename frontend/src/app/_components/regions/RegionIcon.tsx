import Link from 'next/link';

import JejuIcon from '@/../public/icons/regions/jeju.svg';

export default function RegionIcon({
  slug,
  label,
}: {
  slug: string;
  label: string;
}) {
  return (
    <Link
      className="m-4 p-2 flex flex-col inline-flex items-center gap-y-4 text-black no-underline"
      href={slug}
    >
      <div className="w-24 h-24 rounded-full bg-[#000000]/[.4] flex justify-center items-center">
        <JejuIcon className="w-16 h-16" />
      </div>
      {label}
    </Link>
  );
}
