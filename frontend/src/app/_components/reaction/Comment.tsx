'use client';

import { TComment } from '@/types/response';
import RoundProfile from '../ui/profile/RoundProfile';

export default function Comment({ data }: { data: TComment }) {
  const { member_image_url, member_nickname, created_at, content } = data;

  return (
    <section className="flex flex-col">
      <div className="p-4 flex justify-between items-center">
        <div className="flex gap-2 items-center">
          <RoundProfile img={member_image_url} size={40} height="h-[40px]" />
          <span>{member_nickname}</span>
        </div>
        <span className="pr-4 text-gray-400 font-medium">
          {created_at.toLocaleDateString()}
        </span>
      </div>
      <div className="px-4 py-2 min-h-28">{content}</div>
      <div className="px-4 flex gap-4"></div>
    </section>
  );
}
