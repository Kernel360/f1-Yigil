import type { ReactNode } from 'react';

export default function HorizontalScroll({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <section className="flex whitespace-nowrap overflow-x-scroll">
      {children}
    </section>
  );
}
