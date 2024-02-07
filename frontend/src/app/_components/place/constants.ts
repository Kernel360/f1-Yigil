import type { TPlace } from './types';

export const placeData: TPlace[] = [
  {
    id: '1',
    region: '강원',
    liked: true,
    imageUrl: 'https://placehold.co/300x300',
    title: '나의 장소',
    likeCount: 346,
    commentCount: 123,
    rating: 5.0,
  },
  {
    id: '2',
    region: '경기',
    liked: false,
    imageUrl: 'https://placehold.co/300x300',
    title: '나의 멋진 장소',
    likeCount: 42,
    commentCount: 0,
    rating: 4.3,
  },
  {
    id: '3',
    region: '전남',
    liked: false,
    imageUrl: 'https://placehold.co/300x300',
    title: '나의 너무 멋진 장소',
    likeCount: 0,
    commentCount: 42,
    rating: 1.2,
  },
];
