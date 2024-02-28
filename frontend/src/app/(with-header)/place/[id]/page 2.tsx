import { placeDetailSchema } from '@/types/response';

import PlaceDetail from '@/app/_components/place/PlaceDetail';

const url =
  process.env.NODE_ENV === 'production'
    ? process.env.BASE_URL
    : 'http://localhost:8080/api/v1';

export default async function PlaceDetailPage({
  params,
}: {
  params: { id: string };
}) {
  const response = await fetch(`${url}/places/${params.id}`, {
    method: 'GET',
  });

  const body = await response.json();
  const detail = placeDetailSchema.safeParse(body);

  // response parse 실패
  // ErrorBoundary 또는 Suspense 검토 가능
  if (!detail.success) {
    console.log({ message: detail.error.message });

    return <main>Failed</main>;
  }

  return <PlaceDetail detail={detail.data} />;
}
