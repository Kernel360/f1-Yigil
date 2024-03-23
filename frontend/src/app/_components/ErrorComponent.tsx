import React from 'react';

export default function ErrorComponent({
  title,
  reset,
}: {
  title: string;
  reset: () => void;
}) {
  return (
    <div className="w-full h-full flex flex-col break-words justify-center items-center text-3xl text-center text-main">
      {title}를 불러오는데 실패했습니다. <hr />
      다시 시도해주세요.
      <button
        className="bg-main text-white px-2 py-1 rounded-xl my-4"
        onClick={reset}
      >
        재시도
      </button>
    </div>
  );
}
