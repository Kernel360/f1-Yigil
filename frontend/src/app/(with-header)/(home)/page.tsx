import { PostList } from '@/app/_components/post';

import { postData } from '@/app/_components/post/constants';

import PlusIcon from '@/../public/icons/plus.svg';

export default async function HomePage() {
  return (
    <main className="max-w-full flex flex-col gap-8 grow">
      <PostList title="인기 장소" data={postData} />
      <PostList title="관심 지역 장소" data={postData} />
      <div className="w-full sticky bottom-6 flex justify-end">
        <button className="p-4 mr-6 bg-blue-600 rounded-full">
          <PlusIcon />
        </button>
      </div>
    </main>
  );
}
