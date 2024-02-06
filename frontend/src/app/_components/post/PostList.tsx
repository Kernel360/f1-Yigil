'use client';

import useEmblaCarousel from 'embla-carousel-react';

import { Post } from '@/app/_components/post';

import type { TPost } from './Post';
import Link from 'next/link';

export default function PostList({
  title,
  data,
  variant,
}: {
  title: string;
  data: TPost[];
  variant: 'primary' | 'secondary';
}) {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });
  return (
    <section className="flex flex-col" aria-label="posts">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 text-3xl font-medium">{title}</span>
        <span>더보기</span>
      </div>
      <div className="overflow-hidden px-4" ref={emblaRef}>
        <div className="flex">
          {data.map((post, index) => (
            <Link href={`#`} key={post.id}>
              <Post data={post} order={index} variant={variant} />
            </Link>
          ))}
        </div>
      </div>
    </section>
  );
}
