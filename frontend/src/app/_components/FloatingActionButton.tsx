'use client';

import PlusIcon from '@/../public/icons/plus.svg';

export default function FloatingActionButton() {
  return (
    <span className="w-min absolute right-20">
      <button className="fixed bottom-4 w-16 h-16 bg-blue-600 rounded-full flex justify-center items-center">
        <PlusIcon />
      </button>
    </span>
  );
}
