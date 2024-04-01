import CourseDetail from '@/app/_components/mypage/course/CourseDetail';
import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import {
  getCourseDetail,
  getMyPageCourses,
  getMyPageSpots,
  getSpotDetail,
} from '@/app/_components/mypage/hooks/myPageActions';
import SpotDetail from '@/app/_components/mypage/spot/SpotDetail';
import { myInfoSchema } from '@/types/response';
import React from 'react';
import { Metadata } from 'next';
import { createMetadata } from '@/utils';

export async function generateMetadata({
  params,
}: {
  params: { id: number; travel: string };
}): Promise<Metadata> {
  if (params.travel === 'spot') {
    const spotDetail = await getSpotDetail(params.id);
    if (spotDetail.status === 'succeed') {
      const data = {
        title: spotDetail.data.place_name,
        description: spotDetail.data.description,
        image: spotDetail.data.image_urls[0],
        url: `https://yigil.co.kr/detail/spot/${params.id}`,
      };
      return createMetadata(data);
    } else
      return {
        title: '장소 상세 페이지',
        description: '장소 상세 설명',
        openGraph: {
          images: {
            url: '/logo/og-logo.png',
            alt: '이길로그 로고',
          },
        },
        twitter: {
          images: {
            url: '/logo/og-logo.png',
            alt: '이길로그 로고',
          },
        },
      };
  } else {
    const courseDetail = await getCourseDetail(params.id);
    if (courseDetail.status === 'succeed') {
      const data = {
        title: courseDetail.data.title,
        description: courseDetail.data.description,
        image: courseDetail.data.map_static_image_url,
        url: `https://yigil.co.kr/detail/course/${params.id}`,
      };
      return createMetadata(data);
    } else
      return {
        title: '코스 상세 페이지',
        description: '코스 상세 설명',
        openGraph: {
          images: {
            url: '/logo/og-logo.png',
            alt: '이길로그 로고',
          },
        },
        twitter: {
          images: {
            url: '/logo/og-logo.png',
            alt: '이길로그 로고',
          },
        },
      };
  }
}

export default async function SpotDetailPage({
  params,
}: {
  params: { id: number; travel: string };
}) {
  const res = await authenticateUser();
  const parsedUser = myInfoSchema.safeParse(res);
  const user = parsedUser.success ? parsedUser.data : null;

  if (params.travel === 'spot') {
    const spotDetail = await getSpotDetail(params.id);

    if (spotDetail.status === 'failed')
      return (
        <div className="w-full h-full flex flex-col break-words justify-center items-center text-3xl text-center text-main">
          장소 상세 정보를 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
        </div>
      );

    if (!user)
      return <SpotDetail spotDetail={spotDetail.data} spotId={params.id} />;

    const res = await getMyPageSpots();
    const mySpotList =
      res.status === 'succeed'
        ? res.data.content.map((spot) => spot.spot_id)
        : [];

    const isMySpot = mySpotList.includes(Number(params.id));

    return (
      <SpotDetail
        spotDetail={spotDetail.data}
        spotId={params.id}
        isMySpot={isMySpot}
      />
    );
  } else {
    const courseDetail = await getCourseDetail(params.id);
    if (courseDetail.status === 'failed')
      return (
        <div className="w-full h-full flex flex-col break-words justify-center items-center text-3xl text-center text-main">
          코스 상세 정보를 불러오는데 실패했습니다. <hr /> 다시 시도해주세요.
        </div>
      );
    if (!user)
      return (
        <CourseDetail courseDetail={courseDetail.data} courseId={params.id} />
      );

    const res = await getMyPageCourses();
    const myCourseList =
      res.status === 'succeed'
        ? res.data.content.map((course) => course.course_id)
        : [];

    const isMyCourse = myCourseList.includes(Number(params.id));

    return (
      <CourseDetail
        courseDetail={courseDetail.data}
        courseId={params.id}
        isMyCourse={isMyCourse}
      />
    );
  }
}
