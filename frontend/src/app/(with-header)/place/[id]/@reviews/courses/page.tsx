import MemberProvider from '@/context/MemberContext';
import ReportProvider from '@/context/ReportContext';

import Courses from '@/app/_components/place/course/Courses';

import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';
import { getCourses, getReportTypes } from '../action';

import type { TMemberStatus } from '@/context/MemberContext';

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

  const reportTypesResult = await getReportTypes();
  const reportTypes =
    reportTypesResult.status === 'succeed' ? reportTypesResult.data : undefined;

  const result = await getCourses(params.id);

  if (result.status === 'failed') {
    throw result.message;
  }

  return (
    <MemberProvider status={memberStatus}>
      <ReportProvider backendReportTypes={reportTypes?.report_types}>
        <Courses
          placeId={params.id}
          initialCourses={result.data.courses}
          initialHasNext={result.data.has_next}
        />
      </ReportProvider>
    </MemberProvider>
  );
}
