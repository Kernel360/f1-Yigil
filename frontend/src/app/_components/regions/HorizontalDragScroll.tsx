'use client';

import { createContext, useRef, useState } from 'react';

import type { ReactElement } from 'react';
import type { EventFor } from '@/types/type';

export const DragEnabledContext = createContext<boolean>(false);

export default function HorizontalDragScroll({
  children,
}: {
  children: ReactElement[];
}) {
  const containerRef = useRef<HTMLDivElement>(null);

  const [dragEnabled, setDragEnabled] = useState(false);
  const previousX = useRef(0);

  function handleMouseDown(event: EventFor<'div', 'onMouseDown'>) {
    event.preventDefault();

    if (!containerRef.current) {
      return;
    }

    setDragEnabled(true);
    previousX.current = containerRef.current.scrollLeft;
  }

  function handleMouseUp(event: EventFor<'div', 'onMouseUp'>) {
    event.preventDefault();

    setDragEnabled(false);
  }

  function handleMouseMove(event: EventFor<'div', 'onMouseMove'>) {
    event.preventDefault();

    if (!dragEnabled) {
      return;
    }

    requestAnimationFrame(() => {
      if (!containerRef.current) {
        return;
      }

      containerRef.current.scrollBy({
        left: -event.movementX,
      });
    });
  }

  return (
    <div
      ref={containerRef}
      tabIndex={-1}
      onClick={(event) => event.stopPropagation()}
      onMouseDown={handleMouseDown}
      onMouseUp={handleMouseUp}
      onMouseMove={handleMouseMove}
      className="flex justify-between whitespace-nowrap overflow-x-auto"
    >
      {children}
    </div>
  );
}
