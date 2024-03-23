import PlaceDetailWithMySpot from '@/app/_components/place/detail/PlaceDetailWithMySpot';

export default async function PlaceDetailPage({
  params,
}: {
  params: { id: number };
}) {
  return <PlaceDetailWithMySpot placeId={params.id} />;
}
