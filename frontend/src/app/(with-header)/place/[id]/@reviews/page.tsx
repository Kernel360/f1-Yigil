import Spots from '@/app/_components/place/spot/Spots';
import { getSpots } from './action';
import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';
import MemberProvider from '@/context/MemberContext';

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

  const result = await getSpots(params.id);

  if (!result.success) {
    throw result.error;
  }

  const { spots, has_next } = result.data;

  return (
    <MemberProvider status={memberStatus}>
      <Spots
        placeId={params.id}
        initialPage={1}
        initialHasNext={has_next}
        initialSpots={spots}
      />
    </MemberProvider>
  );
}
