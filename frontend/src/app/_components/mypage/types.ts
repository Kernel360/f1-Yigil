export interface TUserInfo {
  nickname: string;
  profile_image_url?: string;
  follower_count: number;
  following_count: number;
}

// 백엔드 타입 변경 가능성 있음
export interface TMyPageSpot {
  spot_id: number;
  image_url: string;
  rate: number;
  created_date: string;
  title: string;
  is_private: boolean;
}

export interface TMyPageCourse {
  course_id: number;
  title: string;
  map_static_image_url: string;
  is_private: boolean;
  created_date: string;
  rate: number;
  spot_count: number;
}

export interface TMyPageBookmark {
  place_id: number;
  place_name: string;
  place_image: string;
  rate: number;
}
/**
 * "course_id": 1,
            "title": "title",
            "description": "description",
            "line_string_json": "…라인스트링 제이슨 어쩌구",
            "rate": 5.0,
            "spot_count": 4,
            "created_at": "날짜"
 */
