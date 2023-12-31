import Link from 'next/link';

export default function RegionIcon({
  slug,
  label,
}: {
  slug: string;
  label: string;
}) {
  return (
    <Link
      className="m-4 p-2 flex flex-col inline-flex items-center gap-y-4"
      href={slug}
    >
      <svg
        className="w-24 h-24"
        viewBox="0 0 100 100"
        xmlns="http://www.w3.org/2000/svg"
      >
        <circle cx="50" cy="50" r="50" fill="#00000099">
          <title>{`${label} 아이콘`}</title>
        </circle>
      </svg>
      {label}
    </Link>
  );
}
