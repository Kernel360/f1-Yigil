'use client';

import { usePathname, useSelectedLayoutSegment } from 'next/navigation';

import NavigationIcon from './NavigationIcon';

import { navigationData } from './constants';

function isLinkActive(pathName: string, segment: string | null, href: string) {
  if (segment === null) {
    if (pathName === href) {
      return true;
    }

    return false;
  }

  return href.slice(1).startsWith(segment);
}

export default function NavigationBar() {
  const pathName = usePathname();
  const segment = useSelectedLayoutSegment();

  return (
    <nav className="w-full flex justify-evenly py-4">
      {navigationData.map(({ label, href, icon }) => {
        return (
          <NavigationIcon
            key={label}
            href={href}
            label={label}
            Icon={icon}
            active={isLinkActive(pathName, segment, href)}
          />
        );
      })}
    </nav>
  );
}
