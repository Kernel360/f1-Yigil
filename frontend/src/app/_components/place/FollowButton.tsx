'use client';

import { useState } from 'react';
import { toggleFollowTravelOwner } from './places/action';
import ToastMsg from '../ui/toast/ToastMsg';
import Spinner from '../ui/Spinner';

export default function FollowButton({
  ownerId,
  initialFollowing,
}: {
  ownerId: number;
  initialFollowing: boolean;
}) {
  const [following, setFollowing] = useState(initialFollowing);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  async function toggleFollow() {
    setIsLoading(true);

    const result = await toggleFollowTravelOwner(ownerId, following);

    if (result.status === 'failed') {
      setError(result.message);
      setTimeout(() => setError(''), 2000);
      setIsLoading(false);
      return;
    }

    setFollowing(!following);
    setIsLoading(false);
  }

  return (
    <button
      className={`p-2 ${following ? '' : 'text-blue-500'}`}
      onClick={toggleFollow}
    >
      {isLoading ? <Spinner size="w-6 h-6" /> : following ? '팔로잉' : '팔로우'}
      {error && <ToastMsg id={Date.now()} title={error} timer={2000} />}
    </button>
  );
}
