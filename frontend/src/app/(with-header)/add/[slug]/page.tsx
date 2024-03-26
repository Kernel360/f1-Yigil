import AddCourse from '@/app/_components/add/course/AddCourse';
import AddSpot from '@/app/_components/add/spot/AddSpot';

import type { TChoosePlace } from '@/context/travel/schema';

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
