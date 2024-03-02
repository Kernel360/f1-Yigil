import type { TPlace, TPlaceDetail } from '@/types/response';

export const placesData: TPlace[] = [
  {
    id: 1,
    place_name: '나의 장소',
    bookmarked: true,
    thumbnail_image_url: 'https://placehold.co/300x300',
    review_count: '123',
    rate: '5.0',
  },
  {
    id: 2,
    place_name: '나의 멋진 장소',
    bookmarked: false,
    thumbnail_image_url: 'https://placehold.co/300x300',
    review_count: '0',
    rate: '4.3',
  },
  {
    id: 3,
    place_name: '나의 너무 멋진 장소',
    bookmarked: true,
    thumbnail_image_url: 'https://placehold.co/300x300',
    review_count: '42',
    rate: '1.2',
  },
];

export const placeDetailData: TPlaceDetail = {
  id: 42,
  place_name: '이구성수',
  address: '서울특별시 성동구 성수동2가 번지 1층 302-4',
  bookmarked: false,
  thumbnail_image_url: 'https://placehold.co/300x200',
  map_static_image_url: 'https://placehold.co/300x200',
  review_count: 13,
  rate: 4.7,
};
