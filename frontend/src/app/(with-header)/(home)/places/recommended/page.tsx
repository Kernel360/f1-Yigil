import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';

import { Place } from '@/app/_components/place/places';
import { getPlaces } from '@/app/_components/place/places/action';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: '맞춤 추천',
  description: `개인별 맞춤 추천 지역의 장소/코스를 만나보세요.`,
  openGraph: {
    title: '맞춤 추천',
    description: `개인별 맞춤 추천 지역의 장소/코스를 만나보세요.`,

    images: { url: '/logo/og-logo.png', alt: '이길로그 로고 이미지' },
    type: 'website',
    siteName: '이길로그',
    locale: 'ko-KR',
  },
  twitter: {
    title: '맞춤 추천',
    description: `개인별 맞춤 추천 지역의 장소/코스를 만나보세요.`,
    images: { url: '/logo/og-logo.png', alt: '이길로그 로고 이미지' },
  },
};

export default async function RecommendedPlacesPage() {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const result = await getPlaces('recommended', 'more');

  if (result.status === 'failed') {
    console.log(result.message);

    return <main>Failed</main>;
  }

  const places = result.data;

  return (
    <main className="px-4 max-w-full flex flex-col">
      <h1 className="pl-4 pt-4 text-3xl font-medium">인기 장소</h1>
      <div className="flex flex-col gap-4 relative">
        {places.map((place, index) => (
          <Place
            key={place.id}
            data={place}
            order={index}
            isLoggedIn={memberInfo.success}
          />
        ))}
      </div>
    </main>
  );
}
