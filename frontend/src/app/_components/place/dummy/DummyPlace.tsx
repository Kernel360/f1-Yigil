import Image from 'next/image';
import IconWithCounts from '../../IconWithCounts';

import BookmarkIcon from '/public/icons/bookmark.svg';
import ReviewIcon from '/public/icons/review.svg';
import StarIcon from '/public/icons/star.svg';

export default function DummyPlace({
  variant,
}: {
  variant?: 'primary' | 'secondary';
}) {
  const postSize =
    variant === 'primary'
      ? 'w-[350px]'
      : variant === 'secondary'
      ? 'w-[300px]'
      : 'w-full';

  return (
    <article
      className={`${postSize} px-2 py-4 relative flex shrink-0 flex-col`}
    >
      <div className="relative w-full">
        <span className="w-full relative aspect-square inline-block w-fit h-fit">
          <Image
            className="rounded-lg select-none"
            src={''}
            alt={`장소 이미지`}
            fill
            sizes="33vw"
          />
        </span>
        <BookmarkIcon className="absolute top-4 right-4 w-12 h-12 stroke-white stroke-[3px] fill-none" />
      </div>
      <section className="flex flex-col gap-2 px-4">
        <span className="w-fit text-gray-500 text-xl font-medium truncate select-none hover:underline">
          장소 이름
        </span>
        <div className="flex gap-3">
          <IconWithCounts icon={<ReviewIcon className="w-4 h-4" />} count={0} />
          <IconWithCounts
            icon={
              <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
            }
            count={0.0}
            rating
          />
        </div>
      </section>
    </article>
  );
}
