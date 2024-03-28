import Image from 'next/image';
import StarIcon from '/public/icons/star.svg';

import { TMyPagePlace } from '@/types/myPageResponse';
import PlaceholderImage from '/public/images/placeholder.png';
import IconWithCounts from '../IconWithCounts';

export default function MyPagePlaceItem({
  place_id,
  place_name,
  place_image,
  rate,
}: TMyPagePlace) {
  return (
    <>
      <div
        className={`flex items-center px-5 py-4  gap-x-4 w-full`}
        aria-label="spot-item"
      >
        <div className="relative w-[100px] h-[100px] shrink-0">
          <Image
            src={place_image || PlaceholderImage}
            alt="spot-image"
            fill
            sizes="100vw"
            className="rounded-md"
          />
        </div>
        <div className="flex flex-col gap-y-2 grow">
          <div className="text-2xl leading-7 text-gray-900 font-semibold ">
            {place_name}
          </div>
          <div className="flex gap-x-2 ml-1 items-center text-xl leading-6 text-gray-500 font-semibold">
            <IconWithCounts
              icon={<StarIcon className="w-4 h-4 fill-[#FBBC05]" />}
              count={rate}
              rating
            />
          </div>
        </div>
      </div>
    </>
  );
}
