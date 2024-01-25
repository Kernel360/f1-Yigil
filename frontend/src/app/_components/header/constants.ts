import MapPin from '/public/icons/map-pin.svg';
import Bell from '/public/icons/bell.svg';
import { signOut } from 'next-auth/react';

export const headerPopOverData = [
  {
    href: '/mypage',
    label: '마이페이지',
    Icon: MapPin,
  },
  {
    href: '/',
    label: '로그아웃',
    Icon: Bell,
    onClick: () => signOut(),
  },
];
