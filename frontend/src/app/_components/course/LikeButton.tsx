'use client';

import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import Spinner from '../ui/Spinner';

import HeartIcon from '/public/icons/heart.svg';

import type { EventFor } from '@/types/type';
import { postLikeCourse } from './action';

import ToastMsg from '../ui/toast/ToastMsg';

export default function LikeButton({
  position,
  sizes,
  travelId,
  liked,
  isLoggedIn,
}: {
  position: string;
  sizes: string;
  travelId: number;
  liked: boolean;
  isLoggedIn: 'true' | 'false';
}) {
  const { push } = useRouter();

  const [travelLiked, setTravelLiked] = useState(liked);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  async function handleClick(event: EventFor<'button', 'onClick'>) {
    event.stopPropagation();
    event.preventDefault();

    if (isLoggedIn === 'false') {
      push('/login');
      return;
    }

    setIsLoading(true);

    const result = await postLikeCourse(travelId, travelLiked);

    if (result.status === 'failed') {
      setError(result.message);
      console.log(result.message);
      setIsLoading(false);
      setTimeout(() => setError(''), 2000);
      return;
    }

    setTravelLiked(!travelLiked);
    setIsLoading(false);
  }

  return (
    <button
      className={`absolute ${position}`}
      onClick={handleClick}
      disabled={isLoading}
    >
      {isLoading ? (
        <Spinner size="w-12 h-12" />
      ) : (
        <HeartIcon
          className={`${sizes} stroke-[3px] ${
            travelLiked ? 'stroke-red-500 fill-red-500' : 'stroke-white'
          }`}
        />
      )}
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </button>
  );
}
