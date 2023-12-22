import NavigationIcon from './NavigationIcon';

import homeIcon from '@/../public/icons/home.svg';
import heartIcon from '@/../public/icons/heart.svg';
import locationIcon from '@/../public/icons/location.svg';
import newIcon from '@/../public/icons/add.svg';
import userIcon from '@/../public/icons/mypage.svg';

const navigationData = [
  { href: '/', imgSrc: homeIcon, label: '홈' },
  {
    href: '/follow',
    imgSrc: heartIcon,
    label: '팔로우',
  },
  {
    href: '/nearby',
    imgSrc: locationIcon,
    label: '주변',
  },
  {
    href: '/new/spot',
    imgSrc: newIcon,
    label: '장소 추가',
  },
  {
    href: '/mypage',
    imgSrc: userIcon,
    label: '마이페이지',
  },
];

export default function NavigationBar() {
  return (
    <nav className="w-full flex justify-evenly py-4">
      {navigationData.map(({ href, imgSrc, label }) => (
        <NavigationIcon
          key={label}
          href={href}
          imgSrc={imgSrc}
          imgAlt={`${label} 아이콘`}
        >
          {label}
        </NavigationIcon>
      ))}
    </nav>
  );
}
