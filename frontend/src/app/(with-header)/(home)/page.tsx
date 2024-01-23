import { PostList } from '@/app/_components/Post';

import { postData } from '@/app/_components/Post/constants';

export default async function HomePage() {
  return (
    <main className="max-w-full flex flex-col gap-8">
      <PostList title="인기 일정" data={postData} />
    </main>
  );
}
