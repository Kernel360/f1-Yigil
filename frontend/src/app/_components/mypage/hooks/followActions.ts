'use server';

import { myPageFollowListSchema } from '@/types/myPageResponse';
import { cookies } from 'next/headers';

const data = [
  {
    member_id: 1,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 2,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 3,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 4,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 5,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 6,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 7,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 9,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 8,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 10,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 11,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
  {
    member_id: 12,
    nickname: 'John',
    profile_image_url: 'https://picsum.photos/50/50',
  },
];

const BASE_URL =
  process.env.ENVIRONMENT === 'production'
    ? process.env.BASE_URL
    : process.env.NEXT_PUBLIC_API_MOCKING !== 'enabled'
    ? process.env.DEV_BASE_URL
    : typeof window === 'undefined'
    ? 'http://localhost:8080/api'
    : 'http://localhost:3000/api';

export const getFollowingList = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'asc',
) => {
  const res = await fetch(
    `${BASE_URL}/v1/follows/followings?page=${pageNo}&size=${size}&sortBy=id&sortOrder=asc`,
    {
      headers: {
        Cookie: `SESSION=${cookies().get('SESSION')?.value}`,
      },
    },
  );
  const followingList = await res.json();
  const parsedFollowList = myPageFollowListSchema.safeParse(followingList);
  return JSON.parse(JSON.stringify(parsedFollowList));
};

export const getFollowerList = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'asc',
) => {
  // const res = await fetch(
  //   `${BASE_URL}/v1/follows/followers?page=${pageNo}&size=${size}&sortBy=id&sortOrder=asc`,
  //   {
  //     headers: {
  //       Cookie: `SESSION=${cookies().get('SESSION')?.value}`,
  //     },
  //   },
  // );
  // const followerList = await res.json();
  // const parsedFollowList = myPageFollowListSchema.safeParse(followerList);
  // return JSON.parse(JSON.stringify(parsedFollowList));

  const sliced = data.slice(pageNo, size + 1);
  if (sliced.length < 4)
    return { success: true, data: { content: sliced, has_next: false } };
  return { success: true, data: { content: sliced, has_next: true } };
};

export const addFollow = async (memberId: number) => {
  const res = await fetch(`${BASE_URL}/v1/follows/follow/${memberId}`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookies().get('SESSION')?.value}`,
    },
  });
  return res.json();
};

export const unFollow = async (memberId: number) => {
  const res = await fetch(`${BASE_URL}/v1/follows/unfollow/${memberId}`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookies().get('SESSION')?.value}`,
    },
  });
  return res.json();
};
