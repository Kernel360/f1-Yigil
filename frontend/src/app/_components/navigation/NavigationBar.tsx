'use client';

import { usePathname } from 'next/navigation';

import NavigationIcon from './NavigationIcon';

import HomeIcon from '/public/icons/home.svg';
import HeartIcon from '/public/icons/heart.svg';
import LocationIcon from '/public/icons/location.svg';
import NewIcon from '/public/icons/add.svg';
import UserIcon from '/public/icons/mypage.svg';

const navigationData = [
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

export default function NavigationBar() {
  const pathName = usePathname();

  return (
    <nav className="w-full flex justify-evenly py-4">
      {navigationData.map(({ label, href, icon }) => (
        <NavigationIcon
          key={label}
          href={href}
          label={label}
          active={pathName === href}
          Icon={icon}
        />
      ))}
    </nav>
  );
}
