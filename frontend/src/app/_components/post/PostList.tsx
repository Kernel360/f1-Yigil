'use client';

import useEmblaCarousel from 'embla-carousel-react';

import { Post } from '@/app/_components/post';

import type { TPost } from './Post';
import Link from 'next/link';

export default function PostList({
  title,
  data,
}: {
  title: string;
  data: TPost[];
}) {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });
  return (
    <section className="flex flex-col" aria-label="posts">
      <div className="flex justify-between px-8">
        <span className="text-2xl">{title}</span>
        <span className="self-center">더보기</span>
      </div>
      <div className="overflow-hidden px-4" ref={emblaRef}>
        <div className="flex">
          {data.map((post) => (
            <Link href={`#`} key={post.id}>
              <Post {...post} />
            </Link>
          ))}
        </div>
      </div>
    </section>
  );
}
