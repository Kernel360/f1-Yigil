import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import BaseSearchBar from '@/app/_components/search/BaseSearchBar';
import BaseSearchContent from '@/app/_components/search/BaseSearchContent';
import MemberProvider, { TMemberStatus } from '@/context/MemberContext';
import SearchProvider from '@/context/search/SearchContext';
import { myInfoSchema } from '@/types/response';

export default async function SearchPage({
  searchParams,
}: {
  searchParams: { keyword: string };
}) {
  const memberJson = await authenticateUser();
  const memberResult = myInfoSchema.safeParse(memberJson);

  const memberStatus: TMemberStatus = memberResult.success
    ? { member: memberResult.data, isLoggedIn: 'true' }
    : { isLoggedIn: 'false' };

  return (
    <main className="flex flex-col grow">
      <MemberProvider status={memberStatus}>
        <SearchProvider showHistory initialKeyword={searchParams.keyword}>
          <BaseSearchBar cancellable />
          <hr className="pt-4 " />
          <BaseSearchContent />
        </SearchProvider>
      </MemberProvider>
    </main>
  );
}
