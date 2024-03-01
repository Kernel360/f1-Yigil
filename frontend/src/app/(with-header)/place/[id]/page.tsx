import { cookies } from 'next/headers';
import PlaceDetail from '@/app/_components/place/PlaceDetail';
import { getPlaceDetail } from '../action';

export default async function PlaceDetailPage({
  params,
}: {
  params: { id: string };
}) {
  const session = cookies().get('SESSION');

  const detail = await getPlaceDetail(params.id);

  // response parse 실패
  // ErrorBoundary 또는 Suspense 검토 가능
  if (!detail.success) {
    console.log({ message: detail.error.message });

    return <main>Failed</main>;
  }

  return (
    <PlaceDetail detail={detail.data} isLoggedIn={session !== undefined} />
  );
}
