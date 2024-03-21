import { Suspense } from 'react';

import { PopularPlaces } from '@/app/_components/place/places';

import DummyPlaces from '@/app/_components/place/dummy/DummyPlaces';
import HomeFAB from './HomeFAB';

export default function LoggedOutHome() {
  return (
    <main className="max-w-full flex flex-col gap-6 relative">
      <Suspense fallback={<DummyPlaces title="관심 지역" variant="primary" />}>
        <PopularPlaces />
      </Suspense>
      <DummyPlaces
        title="관심 지역"
        message="로그인 후 사용 가능합니다."
        variant="secondary"
        needLogin
      />
      <DummyPlaces
        title="맞춤 추천"
        message="로그인 후 사용 가능합니다."
        variant="secondary"
        needLogin
      />
      <HomeFAB />
    </main>
  );
}
