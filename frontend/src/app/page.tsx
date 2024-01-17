import { Post } from './_components/Post';
import SearchBar from './_components/SearchBar';

import { postData } from './_components/Post/constants';

export default async function RootPage() {
  return (
    <main className="pt-[88px] flex flex-col gap-8">
      <SearchBar />
      <Post {...postData} />
    </main>
  );
}
