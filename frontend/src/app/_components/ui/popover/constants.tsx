import MapPin from '/public/icons/map-pin.svg';
import Bell from '/public/icons/bell.svg';
import { signOut } from 'next-auth/react';
import { TPopOverData } from './types';
import LocationIcon from '/public/icons/location.svg';
import CalendarIcon from '/public/icons/calendar.svg';
import UnLockIcon from '/public/icons/unlock.svg';
import TrashIcon from '/public/icons/trash.svg';

export const headerPopOverData: TPopOverData[] = [
  {
    href: '/mypage',
    label: '마이페이지',
    icon: <MapPin className="w-6 h-6" />,
  },
  {
    href: '/',
    label: '로그아웃',
    icon: <Bell className="w-6 h-6" />,
    onClick: () => signOut(),
  },
];

export const homePopOverData: TPopOverData[] = [
  {
    href: '/add/spot',
    label: '장소 추가하기',
    icon: <LocationIcon className="w-6 h-6" />,
  },
  {
    href: '/add/course',
    label: '일정 추가하기',
    icon: <CalendarIcon className="w-6 h-6" />,
  },
];

export const myPagePopOverData: TPopOverData[] = [
  { label: '나만보기 풀기', icon: <UnLockIcon className="w-6 h-6" /> },
  { label: '기록 삭제하기', icon: <TrashIcon classname="w-6 h-6" /> },
  {
    href: '/add/course',
    label: '일정 기록하기',
    icon: <CalendarIcon className="w-6 h-6" />,
  },
];
