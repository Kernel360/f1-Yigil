import { TMyPageSpotType } from '@/app/_components/mypage/spot/MyPageSpotList';

export const myPlaceData: TMyPageSpotType[] = [
  {
    post_id: 1,
    travel_id: 1,
    title: '재밌는 여행',
    image_url: 'https://picsum.photos/seed/picsum/104/104',
    description: '설명',
    isSecret: true,
    post_date: new Date(Date.now()).toLocaleString(),
    rating: 5,
  },
  {
    post_id: 2,
    travel_id: 2,
    title: '잼있는 여행',
    image_url: 'https://picsum.photos/seed/picsum/400/174',
    description: '설영우',
    isSecret: false,
    post_date: new Date(Date.now()).toLocaleString(),
    rating: 4,
  },
];
