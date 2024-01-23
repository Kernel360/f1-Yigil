import type { TPost } from './Post';

export const postData: TPost[] = [
  {
    id: '1',
    region: '강원',
    liked: true,
    imageUrl: 'https://placehold.co/300x300',
    title: '나의 장소',
  },
  {
    id: '2',
    region: '경기',
    liked: false,
    imageUrl: 'https://placehold.co/300x300',
    title: '나의 멋진 장소',
  },
  {
    id: '3',
    region: '전남',
    liked: false,
    imageUrl: 'https://placehold.co/300x300',
    title: '나의 너무 멋진 장소',
  },
];
