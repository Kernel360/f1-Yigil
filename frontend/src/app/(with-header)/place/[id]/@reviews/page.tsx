import MemberProvider from '@/context/MemberContext';
import ReportProvider from '@/context/ReportContext';

import Spots from '@/app/_components/place/spot/Spots';

import { getReportTypes, getSpots } from './action';
import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';

import type { TMemberStatus } from '@/context/MemberContext';

export default async function SpotsPage({
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

  const result = await getSpots(params.id);

  if (result.status === 'failed') {
    throw result.message;
  }

  const { spots, has_next } = result.data;

  return (
    <MemberProvider status={memberStatus}>
      <ReportProvider backendReportTypes={reportTypes?.report_types}>
        <Spots
          placeId={params.id}
          initialPage={1}
          initialHasNext={has_next}
          initialSpots={spots}
        />
      </ReportProvider>
    </MemberProvider>
  );
}
