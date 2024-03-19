import Places from './Places';
import { getPlaces } from './action';

export default async function RecommendedPlaces() {
  const result = await getPlaces('recommended', 'more');

  if (result.status === 'failed') {
    return <>{result.message}</>;
  }

  return (
    <Places
      data={result.data}
      isLoggedIn={false}
      carousel
      variant="secondary"
    />
  );
}
