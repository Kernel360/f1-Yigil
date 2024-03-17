'use client';

import Progress from '../Progress';

export default function SpotProgress({
  currentValue,
}: {
  currentValue: number;
}) {
  return <Progress value={currentValue} />;
}
