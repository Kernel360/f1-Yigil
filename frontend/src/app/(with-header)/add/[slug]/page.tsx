// import AddSpot from '@/app/_components/add/AddSpot';
import AddCourse from '@/app/_components/add/course/AddCourse';
import AddSpot from '@/app/_components/add/spot/AddSpot';

export default function AddPage({
  params,
  searchParams,
}: {
  params: { slug: 'spot' | 'course' };
  searchParams: { keyword: string };
}) {
  const ncpClientId = process.env.NAVER_MAPS_CLIENT_ID;
  const initialKeyword = searchParams.keyword;

  switch (params.slug) {
    case 'spot': {
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
