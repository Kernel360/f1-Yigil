import Spot from '@/app/_components/place/Spot';
import { getSpots } from './action';

export default async function Spots({ params }: { params: { id: number } }) {
  const spotsResult = await getSpots(params.id);

  if (!spotsResult.success) {
    return <section>Failed to get spots</section>;
  }

  const spots = spotsResult.data.spots;

  return (
    <section className="pt-6">
      {spots.map((spot, index) => (
        <Spot key={index} data={spot} />
      ))}
    </section>
  );
}
