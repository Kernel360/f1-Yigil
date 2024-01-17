import LikeButton from './LikeButton';

type TRegion =
  | '강원'
  | '경기'
  | '경남'
  | '경북'
  | '전남'
  | '전북'
  | '제주'
  | '충남'
  | '충북';

export interface TPost {
  id: string;
  region: TRegion;
  liked: boolean;
  imageUrl: string;
  title: string;
  user: {
    profileImageUrl: string;
    nickname: string;
  };
}

// 외부 placeholder 이미지 사용중, no-img-element 린트 에러 발생
// 차후 next/image 사용하게 변경 예정
export default function Post({ region, liked, imageUrl, title, user }: TPost) {
  return (
    <article className="w-[200px] h-[300px] p-2 relative flex flex-col gap-2">
      <span className="absolute top-5 left-4 bg-white px-4 py-1 rounded-full">
        {region}
      </span>
      <LikeButton liked={liked} />
      <img
        className="rounded-lg shadow-lg max-w-full"
        src={imageUrl}
        alt={`${title} 대표 이미지`}
      />
      <p className="text-xl">{title}</p>
      <section className="flex items-center gap-2">
        <img
          className="rounded-full max-w-full"
          src={user.profileImageUrl}
          alt={`${user.nickname} 프로필 이미지`}
        />
        {user.nickname}
      </section>
    </article>
  );
}
