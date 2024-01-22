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
  options,
  data,
}: {
  title: string;
  options: TListOptions[];
  data: TPost[];
}) {
  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });

  return (
    <section className="flex flex-col" aria-label="posts">
      <div className="flex justify-between px-4">
        <span className="text-xl">{title}</span>
        <select name="" id="">
          {options.map(({ slug, label }) => (
            <option key={slug} value={slug}>
              {label}
            </option>
          ))}
        </select>
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
