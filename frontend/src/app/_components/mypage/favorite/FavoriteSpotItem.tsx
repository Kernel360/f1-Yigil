import Image from 'next/image';
import PlaceholderImage from '/public/images/placeholder.png';
import IconWithCounts from '../../IconWithCounts';
import StarIcon from '/public/icons/star.svg';
import { TMyPageFavoriteSpot } from '@/types/myPageResponse';
import RoundProfile from '../../ui/profile/RoundProfile';
import Link from 'next/link';

export default function FavoriteSpotItem({
  place_id,
  spot_id,
  place_name,
  image_url,
  rate,
  writer_nickname,
  writer_profile_image_url,
}: TMyPageFavoriteSpot['contents'][0]) {
  return (
    <div className={`flex items-center gap-x-4 w-full`} aria-label="spot-item">
      <div className="relative w-[100px] h-[100px] shrink-0">
        <Image
          src={image_url || PlaceholderImage}
          alt="spot-image"
          fill
          sizes="100vw"
          className="rounded-md"
        />
      </div>
      <div className="flex flex-col gap-y-2 grow">
        <Link
          href={`/detail/spot/${spot_id}`}
          className="w-fit px-1 text-2xl leading-7 text-gray-900 font-semibold max-w-[200px] truncate hover:underline cursor-pointer"
        >
          {place_name}
        </Link>
        <div className="flex ml-1 gap-x-2 justify-start items-center text-xl leading-6 text-gray-500 font-semibold">
          <RoundProfile img={writer_profile_image_url} size={24} height="h-6" />
          <div className="truncate max-w-[120px] font-semibold text-gray-500">
            {writer_nickname}
          </div>
          <IconWithCounts
            icon={<StarIcon className="w-4 h-4 fill-[#FBBC05]" />}
            count={rate}
            rating
          />
        </div>
      </div>
    </div>
  );
}
