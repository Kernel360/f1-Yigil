import AddSpot from '@/app/_components/add/spot/AddSpot';

export default function TestAddPage() {
  return <AddSpot ncpClientId={process.env.NAVER_MAPS_CLIENT_ID} />;
}
