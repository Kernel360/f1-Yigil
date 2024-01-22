import SearchBar from '../../_components/SearchBar';
import { PostList } from '../../_components/Post';

import { postData, popularListOptions } from '../../_components/Post/constants';

export default async function HomePage() {
  return (
    <main className="flex flex-col gap-8">
      <SearchBar />
      <PostList
        title="인기 일정"
        options={popularListOptions}
        data={postData}
      />
    </main>
  );
}
