'use client';

import useEmblaCarousel from 'embla-carousel-react';

import { Post } from '@/app/_components/Post';

import type { TPost } from './Post';

export interface TListOptions {
  slug: string;
  label: string;
}

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
        <div className="flex justify-between">
          {data.map((post) => (
            <Post key={post.id} {...post} />
          ))}
        </div>
      </div>
    </section>
  );
}
