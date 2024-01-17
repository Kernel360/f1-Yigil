import type { TPost } from './Post';

export const postData: TPost = {
  id: '1',
  region: '강원',
  liked: true,
  imageUrl: 'https://placehold.co/200x200',
  title: '나의 일정',
  user: {
    nickname: '멋진닉네임',
    profileImageUrl: 'https://placehold.co/32x32',
  },
};
