import MapPin from '/public/icons/map-pin.svg';
import Bell from '/public/icons/bell.svg';
import { signOut } from 'next-auth/react';
import { TPopOverData } from './types';
import LocationIcon from '/public/icons/location.svg';
import CalendarIcon from '/public/icons/calendar.svg';

export const headerPopOverData: TPopOverData[] = [
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

export const fabpopOverData: TPopOverData[] = [
  { href: '/add/spot', label: '장소 추가하기', Icon: LocationIcon },
  { href: '/add/course', label: '일정 추가하기', Icon: CalendarIcon },
];
