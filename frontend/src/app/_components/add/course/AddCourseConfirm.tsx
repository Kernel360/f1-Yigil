'use client';

import Image from 'next/image';

import { useContext } from 'react';
import { CourseContext } from '@/context/travel/course/CourseContext';

import IconWithCounts from '../../IconWithCounts';

import StarIcon from '/public/icons/star.svg';
import PlaceholderImage from '/public/images/placeholder.png';
import AddCourseSpots from './AddCourseSpots';

export default function AddCourseConfirm() {
  const [course] = useContext(CourseContext);

  const { spots, review, staticMapImageUrl } = course;

  const currentDate = new Date(Date.now());

  return (
    <section className="px-4 flex flex-col grow">
      <div className="px-2 py-4 flex justify-between">
        <span className="text-2xl font-semibold">{review.title}</span>
        <span className="flex items-center">
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 stroke-[#FACC15] fill-[#FACC15]" />
            }
            count={review.rate}
            rating
          />
        </span>
      </div>
      <AddCourseSpots spots={spots} />
      <div className="my-4 relative aspect-video">
        <Image
          className="rounded-xl object-cover"
          priority
          src={staticMapImageUrl}
          placeholder="blur"
          blurDataURL={PlaceholderImage.blurDataURL}
          alt={`${review.title} 경로 이미지`}
          fill
        />
      </div>
      <span className="self-end text-gray-400 font-medium">
        {currentDate.toLocaleDateString()}
      </span>
      <div className="my-4 h-36">
        <div className="h-full p-4 rounded-xl bg-gray-100">
          {review.content}
        </div>
      </div>
    </section>
  );
}
