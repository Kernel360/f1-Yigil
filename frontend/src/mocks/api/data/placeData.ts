import type { TPlace, TPlaceDetail } from '@/types/response';

export const placesData: TPlace[] = [
  {
    id: 1,
    name: '나의 장소',
    region: '강원',
    liked: true,
    image_url: 'https://placehold.co/300x300',
    liked_count: 346,
    review_count: 123,
    rating: 5.0,
  },
  {
    id: 2,
    name: '나의 멋진 장소',
    region: '경기',
    liked: false,
    image_url: 'https://placehold.co/300x300',
    liked_count: 42,
    review_count: 0,
    rating: 4.3,
  },
  {
    id: 3,
    name: '나의 너무 멋진 장소',
    region: '전남',
    liked: true,
    image_url: 'https://placehold.co/300x300',
    liked_count: 0,
    review_count: 42,
    rating: 1.2,
  },
];

export const placeDetailData: TPlaceDetail = {
  id: 42,
  name: '이구성수',
  address: '서울특별시 성동구 성수동2가 번지 1층 302-4',
  image_url: 'https://placehold.co/300x200',
  map_image_url: 'https://placehold.co/300x200',
  liked_count: 55,
  review_count: 13,
  rating: 4.7,
};
