import { useRouter } from 'next/navigation';
import React from 'react';
import { TPopOverData } from './types';

interface TPopOverIcon {
  data: TPopOverData;
  closeModal: () => void;
}

export default function PopOverIcon({ data, closeModal }: TPopOverIcon) {
  const { push } = useRouter();
  const { href, onClick, label, icon } = data;
  return (
    <div
      onClick={() => {
        onClick && onClick();
        closeModal();
        href && push(href);
      }}
      className="p-2 flex items-center gap-x-2 cursor-pointer"
    >
      <div>{label}</div>
      {icon}
    </div>
  );
}
