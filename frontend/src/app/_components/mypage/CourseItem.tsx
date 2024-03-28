'use client';
import { TMyPageCourse } from '@/types/myPageResponse';
import Image from 'next/image';
import StarIcon from '/public/icons/star.svg';
import LockIcon from '/public/icons/lock-white.svg';

import MapPinIcon from '/public/icons/filled-map-pin.svg';
import { useRouter } from 'next/navigation';
import IconWithCounts from '../IconWithCounts';

export default function CourseItem({
  map_static_image_url,
  is_private,
  course_id,
  title,
  rate,
  spot_count,
  created_date,
}: TMyPageCourse) {
  const { push } = useRouter();
  return (
    <div className="flex flex-col gap-y-2 relative">
      <Image
        src={map_static_image_url || ''}
        alt="course-image"
        width={320}
        height={160}
        className="w-[320px] h-[160px] rounded-md object-cover"
      />

      {is_private && (
        <div className="absolute top-2 right-2 p-3 bg-black rounded-full">
          <LockIcon className="w-5 h-5" />
        </div>
      )}
      <button
        className="text-2xl text-start w-fit leading-7 text-gray-900 font-semibold ml-2 cursor-pointer hover:underline"
        onClick={() => push(`/detail/course/${course_id}`)}
      >
        {title}
      </button>
      <div className="flex gap-x-2 items-center mx-2 text-xl leading-6 text-gray-500 font-semibold">
        <IconWithCounts
          icon={
            <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
          }
          count={rate}
          rating
        />
        <div className="grow text-xl leading-6 text-gray-500 font-semibold">
          <IconWithCounts
            icon={<MapPinIcon className="w-6 h-6" />}
            count={spot_count}
          />
        </div>
        <div className="text-gray-300 font-bold">{created_date}</div>
      </div>
    </div>
  );
}
