import Link from 'next/link';
import DummyPlace from './DummyPlace';

import ChevronRightIcon from '/public/icons/chevron-right.svg';

export default function DummyPlaces({
  title,
  variant,
}: {
  title: string;
  variant?: 'primary' | 'secondary';
}) {
  return (
    <div className="relative flex flex-col">
      <div className="flex justify-between items-center px-4 pt-2">
        <span className="pl-4 text-3xl font-medium">{title}</span>
        <span>
          <ChevronRightIcon className="w-6 h-6 stroke-black stroke-2 [stroke-linecap:round] [stroke-linejoin:round]" />
        </span>
      </div>
      <div className="flex overflow-hidden px-4">
        <DummyPlace variant={variant} />
        <DummyPlace variant={variant} />
      </div>
      <div className="absolute inset-0 bg-white/75 flex flex-col gap-4 justify-center items-center">
        <span className="text-lg font-semibold">
          로그인 후 사용 가능합니다.
        </span>
        <Link className="p-2 rounded-lg bg-main text-white" href="/login">
          로그인
        </Link>
      </div>
    </div>
  );
}
