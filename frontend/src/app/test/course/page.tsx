import AddCourse from '@/app/_components/add/course/AddCourse';

export default function TestAddPage() {
  return <AddCourse ncpClientId={process.env.NAVER_MAPS_CLIENT_ID} />;
}
