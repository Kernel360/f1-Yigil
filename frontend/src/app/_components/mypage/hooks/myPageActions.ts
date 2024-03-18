'use server';

import {
  TMyPageSpotDetail,
  myPageCourseListSchema,
  myPageSpotListSchema,
  mypageSpotDetailSchema,
} from '@/types/myPageResponse';
import { revalidatePath } from 'next/cache';
import { getBaseUrl } from '@/app/utilActions';
import { cookies } from 'next/headers';
import { TModifyDetail } from '../spot/SpotDetail';

export const getMyPageSpots = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  selectOption: string = 'all',
) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(
    `${BASE_URL}/v1/spots/my?page=${pageNo}&size=${size}&sortBy=${
      sortOrder !== 'rate'
        ? `created_at&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`
    }&selected=${selectOption}`,
    {
      headers: {
        Cookie: `SESSION=${cookie}`,
      },
    },
  );
  const spotList = await res.json();
  const parsedSpotList = myPageSpotListSchema.safeParse(spotList);
  return parsedSpotList;
};

export const deleteMySpot = async (spotId: number) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/spots/${spotId}`, {
    method: 'DELETE',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });

  if (res.ok) {
    revalidatePath('/mypage/my/travel/spot');
  }
};

// export const getMyPageCourses = async (
//   pageNo: number = 1,
//   size: number = 5,
//   sortOrder: string = 'desc',
//   selectOption: string = 'all',
// ) => {
//   const courseList = await myPageCourseRequest(
//     `?page=${pageNo}&size=${size}&sortBy=${
//       sortOrder !== 'rate'
//         ? `created_at&sortOrder=${sortOrder}`
//         : `rate&sortOrder=desc`
//     }&selected=${selectOption}`,
//   )()()();
//   const parsedCourseList = myPageCourseListSchema.safeParse(courseList);
//   return parsedCourseList;
// };

// export const deleteMyCourse = async (courseId: number) => {
//   const res = await courseRequest(`/${courseId}`)('DELETE')()();
//   if (res) {
//     revalidatePath('/mypage/my/travel/course');
//     return res;
//   }
// };

export const changeOnPublicMyTravel = async (travel_id: number) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/travels/change-on-public`, {
    method: 'PUT',
    headers: {
      Cookie: `SESSION=${cookie}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(travel_id),
  });
  if (res.ok) {
    revalidatePath('/mypage/my/travel', 'layout');
  }
};

export const changeOnPrivateMyTravel = async (travel_id: number) => {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;
  const res = await fetch(`${BASE_URL}/v1/travels/change-on-private`, {
    method: 'PUT',
    headers: {
      Cookie: `SESSION=${cookie}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(travel_id),
  });
  if (res.ok) {
    revalidatePath('/mypage/my/travel', 'layout');
  }
};

// export const changeTravelsVisibility = async (
//   travel_ids: number[],
//   is_private: boolean,
// ) => {
//   const BASE_URL = await getBaseUrl();
//   const cookie = cookies().get('SESSION')?.value;
//   const data = {
//     travel_ids,
//     is_private,
//   };

//   const res = await fetch(`${BASE_URL}/v1/travels/change-visibility`, {
//     method: 'PUT',
//     headers: {
//       Cookie: `SESSION=${cookie}`,
//       'Content-Type': 'application/json',
//     },
//     body: JSON.stringify(data),
//   });
//   console.log(await res.json());
//   if (res.ok) {
//     revalidatePath('/mypage/my/travel', 'layout');
//   }
// };

// export const getMyPageFollwers = async (
//   pageNo: number = 1,
//   size: number = 5,
//   sortOrder: string = 'desc',
//   sortBy?: string,
// ) => {
//   const res = await myPageFollowerRequest(
//     `?page=${pageNo}&size=${size}&sortBy=${
//       sortOrder !== 'rate' ? `id&sortOrder=${sortOrder}` : `rate&sortOrder=desc`
//     }`,
//   )()()();
//   return res;
// };
// export const getMyPageFollwings = async (
//   pageNo: number = 1,
//   size: number = 5,
//   sortOrder: string = 'desc',
//   sortBy?: string,
// ) => {
//   const res = await myPageFollowingRequest(
//     `?page=${pageNo}&size=${size}&sortOrder=${sortBy ? 'desc' : sortOrder}${
//       sortBy ? '&sortBy=rate' : ''
//     }`,
//   )()()();
//   return res;
// };

export const getMyPageSpotDetail = async (spotId: number) => {
  const BASE_URL = await getBaseUrl();
  const res = await fetch(`${BASE_URL}/v1/spots/${spotId}`);
  const result = await res.json();
  const parsedSpotDetail = mypageSpotDetailSchema.safeParse(result);
  return parsedSpotDetail;
};

export const patchMyPageSpotDetail = async (
  spotId: number,
  modifiedData: TModifyDetail,
) => {
  const { id, description, rate, image_urls } = modifiedData;
  const formData = new FormData();

  const originalSpotImages = image_urls.filter((image) => {
    if (!image.uri.startsWith('data')) {
      return { imageUrl: image.uri, index: Number(image.filename) };
    }
  });

  const updateSpotImagesIdx = image_urls.filter((image, idx) => {
    if (image.uri.startsWith('data')) return idx;
  });

  formData.append('id', id.toString());
  formData.append('description', description);
  formData.append('rate', rate);

  formData.append('originalSpotImages', originalSpotImages);

  // formdata 설정 및 spot image 배열 데이터 어떻게 들어갈지 물어보기
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get(`SESSION`)?.value;
  const res = await fetch(`${BASE_URL}/v1/spots/${spotId}`, {
    method: 'POST',
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });
};
