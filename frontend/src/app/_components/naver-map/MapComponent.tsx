'use client';
import React, { ReactNode } from 'react';
import { Container as MapDiv } from 'react-naver-maps';

export default function MapComponent({
  width,
  height,
  children,
}: {
  width: string;
  height: string;
  children: ReactNode;
}) {
  return (
    <MapDiv
      style={{
        width,
        height,
      }}
    >
      {children}
    </MapDiv>
  );
}
