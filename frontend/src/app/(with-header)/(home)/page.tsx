import { PostList } from '@/app/_components/Post';

import { postData } from '@/app/_components/Post/constants';

import PlusIcon from '@/../public/icons/plus.svg';

export default async function HomePage() {
  return (
    <main className="relative max-w-full flex flex-col gap-8 grow">
      <PostList title="인기 일정" data={postData} />
      <button className="absolute p-4 bottom-10 right-6 bg-blue-600 rounded-full">
        <PlusIcon />
      </button>
    </main>
  );
}
