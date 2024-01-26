import React, { useState } from 'react';
import UnLockIcon from '/public/icons/unlock.svg';

interface PropsType {
  post_id: number;
  travel_id: number;
  title: string;
  imageUrl: string;
  description: string;
  isSecret: boolean;
  post_date: string;
}

export default function MyPagePlaceItem({
  imageUrl,
  post_id,
  travel_id,
  title,
  description,
  isSecret,
  post_date,
}: PropsType) {
  const [secret, setSecret] = useState<boolean>(isSecret);
  return (
    <div
      key={travel_id}
      // style={{ backgroundImage: `url(${imageUrl})` }}
      className="mt-3 bg-gray-200 w-[396px] h-[174px] mx-auto rounded-md p-1 flex flex-col justify-between"
    >
      <div className="flex justify-end mx-1 my-1">
        <span
          className={`p-2 ${
            secret ? 'bg-gray-400' : 'bg-white'
          } rounded-full cursor-pointer`}
          onClick={() => {
            setSecret(!secret);
          }}
        >
          <UnLockIcon />
        </span>
      </div>
      <div className="flex items-end justify-between m-2">
        <div className="text-gray-700 text-3xl font-semibold">{title}</div>
        <div className="text-gray-500 text-xs">{post_date.slice(0, 10)}</div>
      </div>
    </div>
  );
}
