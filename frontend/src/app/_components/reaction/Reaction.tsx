'use client';

import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import { MemberContext } from '@/context/MemberContext';
import Comments from './Comments';
import { postResponseSchema } from '@/types/response';
import { postLike } from '../place/spot/action';

import CommentIcon from '/public/icons/comment.svg';
import HeartIcon from '/public/icons/heart.svg';

/**
 * @todo 좋아요 애니메이션
 */
export default function Reaction({
  placeId,
  travelId,
  initialLiked,
}: {
  placeId: number;
  travelId: number;
  initialLiked: boolean;
}) {
  const { push } = useRouter();

  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [liked, setLiked] = useState(initialLiked);

  const memberStatus = useContext(MemberContext);

  async function handleLike() {
    if (memberStatus.isLoggedIn === 'false') {
      push('/login');
      return;
    }

    setIsLoading(true);

    const json = JSON.parse(await postLike(placeId, travelId, liked));

    const result = postResponseSchema.safeParse(json);

    if (!result.success) {
      console.log(result.error.message);
      setIsLoading(false);
      return;
    }

    setLiked(!liked);
    setIsLoading(false);
  }

  return (
    <div className="flex flex-col">
      <div className="flex">
        <button
          className={`py-2 flex gap-2 justify-center items-center grow ${
            isOpen && 'bg-black'
          }`}
          onClick={() => setIsOpen(!isOpen)}
        >
          <CommentIcon
            className={`w-6 h-6 ${isOpen ? 'stroke-white' : 'stroke-gray-500'}`}
          />
          <span
            className={`font-light ${isOpen ? 'text-white' : 'text-gray-500'}`}
          >
            댓글
          </span>
        </button>
        <button
          className={`py-2 flex gap-2 justify-center items-center grow ${
            isLoading && 'bg-gray-100'
          }`}
          onClick={handleLike}
          disabled={isLoading}
        >
          <HeartIcon
            className={`w-6 h-6 stroke-2 ${
              liked ? 'stroke-red-500 fill-red-500' : 'stroke-gray-500'
            }`}
          />
          <span
            className={`font-light ${liked ? 'text-black' : 'text-gray-500'}`}
          >
            좋아요
          </span>
        </button>
      </div>

      {isOpen && <Comments parentId={travelId} />}
    </div>
  );
}
