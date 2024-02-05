import { TMyPageCourse } from '@/app/_components/mypage/course/MyPageCourseList';
import { TMyPageSpot } from '@/app/_components/mypage/spot/MyPageSpotList';

export const myPlaceSpotData: TMyPageSpot[] = [
  {
    postId: 1,
    travel_id: 1,
    title: '재밌는 여행',
    image_url: 'https://picsum.photos/seed/picsum/104/104',
    description: '설명',
    isSecret: true,
    post_date: new Date(Date.now()).toLocaleString(),
    rating: 5,
  },
  {
    postId: 2,
    travel_id: 2,
    title: '잼있는 여행',
    image_url: 'https://picsum.photos/seed/picsum/400/174',
    description: '설영우',
    isSecret: false,
    post_date: new Date(Date.now()).toLocaleString(),
    rating: 4,
  },
  {
    postId: 3,
    travel_id: 2,
    title: '잼있는1 여행',
    image_url: 'https://picsum.photos/seed/picsum/400/174',
    description: '설영우',
    isSecret: false,
    post_date: new Date(Date.now()).toLocaleString(),
    rating: 3,
  },
];
export const myPlaceCourseData: TMyPageCourse[] = [
  {
    course_id: 1,
    travel_id: 1,
    title: '재밌는 여행',
    image_url: 'https://picsum.photos/seed/picsum/320/160',
    isSecret: true,
    post_date: new Date(Date.now()).toLocaleString(),
    rating: 5,
    spots: 5,
  },
  {
    course_id: 2,
    travel_id: 2,
    title: '코스 여행',
    image_url: 'https://picsum.photos/seed/picsum/320/160',
    isSecret: false,
    post_date: new Date(Date.now()).toLocaleString(),
    rating: 4,
    spots: 3,
  },
];
