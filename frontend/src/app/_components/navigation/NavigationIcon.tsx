import Link from 'next/link';

import type { ComponentType } from 'react';

export default function NavigationIcon({
  href,
  label,
  active,
  Icon,
}: {
  href: string;
  label: string;
  active?: boolean;
  Icon: ComponentType<{ className?: string }>;
}) {
  const activeLinkStyle = active ? 'text-main' : '';

  return (
    <Link
      href={href}
      className={`w-16 flex flex-col gap-2 justify-center items-center text-xs text-[#000000]/[.4] no-underline hover:text-main focus:text-main ${activeLinkStyle}`}
    >
      <Icon className={`${active ? 'fill-main' : 'fill-[#000000]/[.4]'}`} />
      {label}
    </Link>
  );
}
