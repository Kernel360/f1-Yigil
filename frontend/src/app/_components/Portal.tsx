'use client';
import React, { useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import PopOver from './ui/popover/PopOver';

interface TPortalType {
  popOverData: {
    href: string;
    label: string;
    Icon: React.ComponentType<{
      className?: string | undefined;
    }>;
    onClick?: (() => void) | undefined;
  }[];
  style?: string;
  closeModal: any;
  position?: string;
  backdropStyle?: string;
}
export default function ViewPortal({
  popOverData,
  position,
  closeModal,
  style,
  backdropStyle,
}: TPortalType) {
  const [element, setElement] = useState<HTMLElement | null>(null);

  useEffect(() => {
    setElement(document.getElementById('modal'));
  }, []);
  if (!element) return <></>;

  return (
    <>
      {createPortal(
        <>
          <div
            className={`fixed inset-0 max-w-[430px] mx-auto z-20 ${backdropStyle}`}
            onClick={closeModal}
          >
            <PopOver
              popOverData={popOverData}
              closeModal={closeModal}
              position={position}
              style={style}
            />
          </div>
        </>,
        element,
      )}
    </>
  );
}
