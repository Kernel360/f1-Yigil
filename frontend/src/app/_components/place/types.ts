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

export interface TPlace {
  id: number;
  region: TRegion;
  liked: boolean;
  imageUrl: string;
  title: string;
  likeCount: number;
  commentCount: number;
  rating: number;
}
