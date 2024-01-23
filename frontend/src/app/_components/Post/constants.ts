import type { TPost } from './Post';
import type { TListOptions } from './PostList';

export const postData: TPost[] = [
  {
    id: '1',
    region: '강원',
    liked: true,
    imageUrl: 'https://placehold.co/200x200',
    title: '나의 일정',
    user: {
      nickname: '멋진닉네임',
      profileImageUrl: 'https://placehold.co/32x32',
    },
  },
  {
    id: '2',
    region: '경기',
    liked: false,
    imageUrl: 'https://placehold.co/200x200',
    title: '나의 멋진 일정',
    user: {
      nickname: '조금멋진닉네임',
      profileImageUrl: 'https://placehold.co/32x32',
    },
  },
  {
    id: '3',
    region: '전남',
    liked: false,
    imageUrl: 'https://placehold.co/200x200',
    title: '나의 너무 멋진 일정',
    user: {
      nickname: '약간멋진닉네임',
      profileImageUrl: 'https://placehold.co/32x32',
    },
  },
];
