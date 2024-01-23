import { PostList } from '@/app/_components/Post';

import { postData } from '@/app/_components/Post/constants';

import PlusIcon from '@/../public/icons/plus.svg';

export default async function HomePage() {
  return (
    <main className="max-w-full flex flex-col gap-8 grow">
      <PostList title="인기 장소" data={postData} />
      <PostList title="관심 지역 장소" data={postData} />
      <button className="fixed p-4 bottom-8 bg-blue-600 rounded-full">
        <PlusIcon />
      </button>
    </main>
  );
}
