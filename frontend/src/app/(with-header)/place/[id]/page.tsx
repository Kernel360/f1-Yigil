import Image from 'next/image';

import BackButton from './BackButton';
import LikeButton from './LikeButton';
import IconWithCounts from '@/app/_components/IconWithCounts';
import { placeDetailSchema } from '@/types/response';

import ReviewIcon from '/public/icons/review.svg';
import HeartIcon from '/public/icons/heart.svg';
import StarIcon from '/public/icons/star.svg';
import LocationIcon from '/public/icons/map-pin.svg';

const url =
  process.env.NODE_ENV === 'production'
    ? process.env.BASE_URL
    : 'http://localhost:8080/api/v1';

export default async function PlaceDetailPage({
  params,
}: {
  params: { id: string };
}) {
  const response = await fetch(`${url}/places/${params.id}`, {
    method: 'GET',
  });

  const body = await response.json();

  // fetch call 실패
  if (!response.ok) {
    console.log({ message: response.status });

    return <main>Failed</main>;
  }

  const detail = placeDetailSchema.safeParse(body);

  // response parse 실패
  if (!detail.success) {
    console.log({ message: detail.error.message });

    return <main>Failed</main>;
  }

  const {
    name,
    address,
    image_url,
    map_image_url,
    review_count,
    liked_count,
    rating,
  } = detail.data;

  const hasReview = false;

  return (
    <main className="flex flex-col">
      <nav className="relative py-4 flex justify-center items-center">
        <BackButton className="absolute left-2" />
        <span className="text-2xl font-light">장소 상세</span>
      </nav>
      <section className="flex flex-col">
        <div className="relative aspect-square shrink-0">
          <Image
            className="object-cover"
            src={image_url}
            alt={`${name} 대표 이미지`}
            fill
            unoptimized
          />
          <LikeButton className="absolute top-4 right-4" />
        </div>
        <div className="p-2 flex flex-col gap-2">
          <h1 className="text-2xl font-semibold">{name}</h1>
          <div className="flex gap-3 items-center">
            <IconWithCounts
              icon={<ReviewIcon className="w-4 h-4" />}
              count={review_count}
            />
            <IconWithCounts
              icon={<HeartIcon className="w-4 h-4" />}
              count={liked_count}
            />
            <IconWithCounts
              icon={
                <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
              }
              count={rating}
              rating
            />
          </div>
        </div>
        <hr className="border-8" />
        <div className="p-4 flex flex-col">
          <h2 className="text-xl font-medium">지도</h2>
          <div className="h-80 relative">
            <Image
              className="aspect-video"
              src={map_image_url}
              alt={`${name} 위치 이미지`}
              fill
              unoptimized
            />
          </div>
          <span className="flex items-center gap-2">
            <LocationIcon className="w-6 h-6 stroke-gray-500" />
            <p className="text-gray-500">{address}</p>
          </span>
        </div>
      </section>
      <hr className="border-8" />
      <section className="p-2">{hasReview ? <></> : <></>}</section>
      <hr className="border-8" />
    </main>
  );
}
