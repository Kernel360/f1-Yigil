import TabGroup from '@/app/_components/place/TabGroup';
import type { ReactElement } from 'react';

export default function ReviewsLayout({
  params,
  children,
}: {
  params: { id: number };
  children: ReactElement;
}) {
  return (
    <section className="px-4">
      <h2 className="py-2 text-xl font-medium" id="reviews">
        리뷰
      </h2>
      <TabGroup
        path={`/place/${params.id}`}
        items={[{ text: '장소' }, { text: '코스', slug: 'courses' }]}
        label="reviews"
      />
      {children}
    </section>
  );
}
