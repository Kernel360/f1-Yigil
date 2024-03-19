import Places from './Places';
import { getRecommendedPlaces } from './action';

export default async function RecommendedPlaces() {
  const recommended = await getRecommendedPlaces();

  if (recommended.status === 'failed') {
    return <>{recommended.message}</>;
  }

  return (
    <Places
      data={recommended.data}
      isLoggedIn={false}
      carousel
      variant="secondary"
    />
  );
}
