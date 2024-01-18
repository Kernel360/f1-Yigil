import SearchBar from './_components/SearchBar';
import { PostList } from './_components/Post';

import { postData, popularListOptions } from './_components/Post/constants';

export default async function RootPage() {
  return (
    <main className="pt-[88px] flex flex-col gap-8">
      <SearchBar />
      <PostList
        title="인기 일정"
        options={popularListOptions}
        data={postData}
      />
    </main>
  );
}
