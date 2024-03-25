'use client';

import Progress from '../Progress';

export default function SpotProgress({
  currentValue,
}: {
  currentValue: number;
}) {
  return currentValue < 4 && <Progress value={currentValue} />;
}
