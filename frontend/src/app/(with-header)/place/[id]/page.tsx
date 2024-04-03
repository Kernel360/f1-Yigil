import PlaceDetailWithMySpot from '@/app/_components/place/detail/PlaceDetailWithMySpot';
import { getPlaceDetail } from '@/app/_components/place/detail/action';
import { createMetadata } from '@/utils';

export async function generateMetadata({ params }: { params: { id: number } }) {
  const detailResult = await getPlaceDetail(params.id);
  if (detailResult.success) {
    const data = {
      title: detailResult.data.place_name,
      description: detailResult.data.address,
      image: detailResult.data.thumbnail_image_url,
      url: `https://yigil.co.kr/place/${params.id}`,
    };

    return createMetadata(data);
  }
}

export default async function PlaceDetailPage({
  params,
}: {
  params: { id: number };
}) {
  return <PlaceDetailWithMySpot placeId={params.id} />;
}
