import Link from 'next/link';
import React, { ComponentType, Dispatch, SetStateAction } from 'react';

interface TPopOverIcon {
  href: string;
  onClick?: () => void;
  label: string;
  setIsModalOpened: Dispatch<SetStateAction<boolean>>;
  Icon: ComponentType<{ className?: string }>;
}

export default function PopOverIcon({
  href,
  onClick,
  label,
  setIsModalOpened,
  Icon,
}: TPopOverIcon) {
  return (
    <Link
      key={href}
      href={href}
      onClick={() => {
        onClick && onClick();
        setIsModalOpened(false);
      }}
      className="flex items-center"
    >
      <div>{label}</div>
      <Icon />
    </Link>
  );
}
