'use client';
import React, { ReactNode, useEffect, useState } from 'react';
import { createPortal } from 'react-dom';

interface TPortalType {
  closeModal: () => void;
  backdropStyle?: string;
  children: ReactNode;
}
export default function ViewPortal({
  closeModal,
  backdropStyle,
  children,
}: TPortalType) {
  const [element, setElement] = useState<HTMLElement | null>(null);

  useEffect(() => {
    setElement(document.getElementById('modal'));
  }, []);
  if (!element) return <></>;

  return createPortal(
    <div
      className={`fixed inset-0 max-w-[430px] mx-auto z-20 ${backdropStyle}`}
      onClick={closeModal}
    >
      {children}
    </div>,
    element,
  );
}
