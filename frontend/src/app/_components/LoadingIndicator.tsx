import React from 'react';

import Spinner from './ui/Spinner';

interface TLoadingIndicator {
  style?: string;
  size?: string;
  backgroundColor?: string;
  loadingText: string;
}

export default function LoadingIndicator({
  style,
  size,
  backgroundColor,
  loadingText,
}: TLoadingIndicator) {
  return (
    <div
      role="status"
      className={`w-fit h-fit flex flex-col items-center justify-center px-2 py-1 rounded-md ${style} ${backgroundColor}`}
    >
      <Spinner size={size} />
      <span className="text-main my-1">{loadingText}</span>
    </div>
  );
}
