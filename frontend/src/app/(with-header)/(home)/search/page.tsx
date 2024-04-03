import MemberProvider from '@/context/MemberContext';
import SearchProvider from '@/context/search/SearchContext';
import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import { myInfoSchema } from '@/types/response';

import type { TMemberStatus } from '@/context/MemberContext';
import BackendSearchBar from '@/app/_components/place/BackendSearchBar';
import TravelSearchResult from '@/app/_components/search/TravelSearchResult';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: '이길로그 검색 페이지',
  description: '원하는 장소/코스를 검색해보세요.',
};

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
          <BackendSearchBar />
          <hr className="my-4" />
          <TravelSearchResult />
        </SearchProvider>
      </MemberProvider>
    </main>
  );
}
