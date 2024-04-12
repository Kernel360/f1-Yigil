import AddCourse from '@/app/_components/add/course/AddCourse';
import AddSpot from '@/app/_components/add/spot/AddSpot';
import type { TChoosePlace } from '@/context/travel/schema';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: '장소/코스 추가',
  description: `원하는 장소/코스를 추가하고 공유해보세요.`,
  openGraph: {
    title: '장소/코스 추가',
    description: `원하는 장소/코스를 추가하고 공유해보세요.`,
    images: { url: '/logo/og-logo.png', alt: '이길로그 로고 이미지' },
    type: 'website',
    siteName: '이길로그',
    locale: 'ko-KR',
  },
  twitter: {
    title: '장소 / 코스 추가',
    description: `원하는 장소/코스를 추가하고 공유해보세요.`,
    images: { url: '/logo/og-logo.png', alt: '이길로그 로고 이미지' },
  },
};

export default function AddPage({
  params,
  searchParams,
}: {
  params: { slug: 'spot' | 'course' };
  searchParams: {
    keyword: string;
    name?: string;
    address?: string;
    lat?: string;
    lng?: string;
    mapImageUrl?: string;
  };
}) {
  const ncpClientId = process.env.NAVER_MAPS_CLIENT_ID;
  const initialKeyword = searchParams.keyword;

  switch (params.slug) {
    case 'spot': {
      const { name, address, mapImageUrl, lat, lng } = searchParams;

      if (name && address && mapImageUrl && lat && lng) {
        const initalPlace: TChoosePlace = {
          name,
          address,
          mapImageUrl,
          coords: { lat: Number.parseFloat(lat), lng: Number.parseFloat(lng) },
        };

        return (
          <AddSpot
            initialPlace={initalPlace}
            ncpClientId={ncpClientId}
            initialKeyword={initialKeyword}
          />
        );
      }

      return (
        <AddSpot ncpClientId={ncpClientId} initialKeyword={initialKeyword} />
      );
    }
    case 'course': {
      return (
        <AddCourse ncpClientId={ncpClientId} initialKeyword={initialKeyword} />
      );
    }
  }
}
