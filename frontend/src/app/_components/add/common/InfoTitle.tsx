import React from 'react';

interface TInfoTitle {
  label: string;
  additionalLabel: string;
  textSizeAndLineHeight?: string;
  fontBold?: string;
  height?: string;
}

export default function InfoTitle({
  label,
  additionalLabel,
  textSizeAndLineHeight,
  fontBold,
  height,
}: TInfoTitle) {
  return (
    <div
      className={`flex flex-col justify-center pl-8 ${
        textSizeAndLineHeight ? textSizeAndLineHeight : 'text-[32px] leading-10'
      } ${fontBold ? fontBold : 'font-semibold'} ${height ? height : ' h-1/3'}`}
    >
      <span className="text-blue-400">
        {label}
        <span className="text-black">{additionalLabel.slice(0, 1)}</span>
      </span>
      <span className="text-black">
        {additionalLabel.slice(1, additionalLabel.length)}
      </span>
    </div>
  );
}
