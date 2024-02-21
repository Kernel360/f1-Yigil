import type { ReactElement } from 'react';

export default function IconWithCounts({
  icon,
  count,
  rating,
}: {
  icon: ReactElement;
  count: number;
  rating?: boolean;
}) {
  const label = rating ? count.toFixed(1) : count >= 100 ? '99+' : count;

  return (
    <div className="flex items-center">
      {icon}
      <p className="pl-2 select-none flex justify-center">{label}</p>
    </div>
  );
}
