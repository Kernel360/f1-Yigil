import Spots from '@/app/_components/place/spot/Spots';
import { getSpots } from './action';

export default async function SpotsPage({
  params,
}: {
  params: { id: number };
}) {
  const spotsResult = await getSpots(params.id);

  if (!spotsResult.success) {
    return <section>Failed to get spots</section>;
  }

  const { spots, has_next } = spotsResult.data;

  return (
    <Spots
      placeId={params.id}
      initialPage={1}
      initialHasNext={has_next}
      initialSpots={spots}
    />
  );
}
