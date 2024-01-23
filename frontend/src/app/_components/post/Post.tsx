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
}

// 외부 placeholder 이미지 사용중, no-img-element 린트 에러 발생
// 차후 next/image 사용하게 변경 예정
export default function Post({ region, liked, imageUrl, title }: TPost) {
  return (
    <article className="w-[300px] h-[350px] p-2 relative flex shrink-0 flex-col gap-2">
      <span className="absolute top-5 left-4 bg-white px-4 py-1 rounded-full">
        {region}
      </span>
      <LikeButton liked={liked} />
      <img
        className="rounded-lg shadow-lg max-w-full"
        src={imageUrl}
        alt={`${title} 대표 이미지`}
      />
      <section className="flex items-center gap-2 px-4">
        <p className="text-xl">{title}</p>
        {/* 평점 */}
      </section>
    </article>
  );
}
