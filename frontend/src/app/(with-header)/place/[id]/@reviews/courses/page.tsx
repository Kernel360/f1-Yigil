import MemberProvider from '@/context/MemberContext';

import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';
import { getCourses } from '../action';

import type { TMemberStatus } from '@/context/MemberContext';
import Courses from '@/app/_components/place/course/Courses';

export default async function CoursesPage({
  params,
}: {
  params: { id: number };
}) {
  const memberJson = await authenticateUser();

  const memberResult = myInfoSchema.safeParse(memberJson);
  const memberStatus: TMemberStatus = memberResult.success
    ? { member: memberResult.data, isLoggedIn: 'true' }
    : { isLoggedIn: 'false' };

  const result = await getCourses(params.id);

  if (result.status === 'failed') {
    throw result.message;
  }

  return (
    <MemberProvider status={memberStatus}>
      <Courses
        placeId={params.id}
        initialCourses={result.data.courses}
        initialHasNext={result.data.has_next}
      />
    </MemberProvider>
  );
}
