import FloatingActionButton from '@/app/_components/FloatingActionButton';
import { PostList } from '@/app/_components/post';
import { postData } from '@/app/_components/post/constants';
import { homePopOverData } from '@/app/_components/ui/popover/constants';

export default async function HomePage() {
  return (
    <main className="max-w-full flex flex-col gap-4 relative">
      <PostList title="인기" variant="primary" data={postData} />
      <PostList title="관심 지역" variant="secondary" data={postData} />
      <FloatingActionButton
        popOverData={homePopOverData}
        backdropStyle="bg-black bg-opacity-10"
      />
    </main>
  );
}
