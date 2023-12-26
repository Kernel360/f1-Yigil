import HomeIcon from '/public/icons/home.svg';
import HeartIcon from '/public/icons/heart.svg';
import LocationIcon from '/public/icons/location.svg';
import NewIcon from '/public/icons/add.svg';
import UserIcon from '/public/icons/mypage.svg';

export const navigationData = [
  {
    label: '홈',
    href: '/',
    icon: HomeIcon,
  },
  {
    label: '팔로우',
    href: '/follow',
    icon: HeartIcon,
  },
  {
    label: '주변',
    href: '/nearby',
    icon: LocationIcon,
  },
  {
    label: '경로 추가',
    href: '/new/spot',
    icon: NewIcon,
  },
  {
    label: '마이페이지',
    href: '/mypage',
    icon: UserIcon,
  },
];
