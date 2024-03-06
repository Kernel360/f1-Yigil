import { getSpots } from './action';

export default async function Spots({ params }: { params: { id: number } }) {
  const spotsResult = await getSpots(params.id);

  if (!spotsResult.success) {
    return <section>Failed to get spots</section>;
  }

  return <section>Spots</section>;
}
