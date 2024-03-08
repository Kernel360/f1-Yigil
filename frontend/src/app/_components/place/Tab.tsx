'use client';

import Link from 'next/link';
import { useSelectedLayoutSegment } from 'next/navigation';

export interface Item {
  text: string;
  slug?: string;
  segment?: string;
  parallelRoutesKey?: string;
}

export default function ReviewTab({
  path,
  parallelRoutesKey,
  label,
  item,
}: {
  path: string;
  parallelRoutesKey?: string;
  label?: string;
  item: Item;
}) {
  const destination = item.slug ? `${path}/${item.slug}` : path;
  const href = label ? `${destination}#${label}` : destination;

  const segment = useSelectedLayoutSegment(parallelRoutesKey);
  const isActive =
    (!item.slug && segment === null) ||
    segment === item.segment ||
    segment === item.slug;

  return (
    <Link
      className={`text-lg font-medium ${
        isActive ? 'border-b-4 border-black' : 'text-gray-500'
      }`}
      href={href}
    >
      {item.text}
    </Link>
  );
}
