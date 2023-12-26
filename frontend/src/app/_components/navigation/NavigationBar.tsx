'use client';

import { usePathname } from 'next/navigation';

import NavigationIcon from './NavigationIcon';

import { navigationData } from './constants';

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
