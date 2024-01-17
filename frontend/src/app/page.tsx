import { Post } from './_components/Post';

import { postData } from './_components/Post/constants';

export default async function RootPage() {
  return (
    <main className="pt-[88px]">
      <Post {...postData} />
    </main>
  );
}
