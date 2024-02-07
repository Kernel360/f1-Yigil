import { httpRequest } from '../../api/httpRequest';
import { TMyPageSpot } from '../spot/MyPageSpotList';

export const myPageSpotRequest = httpRequest('members/spots');
export const myPageCourseRequest = httpRequest('member/courses');
export const myPageFollowerRequest = httpRequest('member/followers');
export const myPageFollowingRequest = httpRequest('member/followings');

export const getMyPageSpot = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  if (sortBy === 'rate')
    return myPageSpotRequest(
      `?page=${pageNo}&size=${size}&sortBy=${sortBy}`,
    )()();
  else
    return myPageSpotRequest(
      `?page=${pageNo}&size=${size}&sortOrder=${sortOrder}`,
    )()({})();
};

export const deleteMyPageSpot = (spotId: number) => {
  return myPageSpotRequest(`${spotId}`)('DELETE')()();
};

export const getMyPageCourse = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  if (sortBy === 'rate')
    return myPageCourseRequest(
      `?page=${pageNo}&size=${size}&sortBy=${sortBy}`,
    )()()();
  else
    return myPageCourseRequest(
      `?page=${pageNo}&size=${size}&sortOrder=${sortOrder}`,
    )()()();
};

export const deleteMyCourseSpot = (courseId: number) => {
  return myPageCourseRequest(`${courseId}`)('DELETE')()();
};

export const getMyPageFollwers = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  if (sortBy === 'rate')
    return myPageFollowerRequest(
      `?page=${pageNo}&size=${size}&sortBy=${sortBy}`,
    )()()();
  else
    return myPageFollowerRequest(
      `?page=${pageNo}&size=${size}&sortOrder=${sortOrder}`,
    )()()();
};
export const getMyPageFollwings = (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  sortBy?: string,
) => {
  if (sortBy === 'rate')
    return myPageFollowingRequest(
      `?page=${pageNo}&size=${size}&sortBy=${sortBy}`,
    )()()();
  else
    return myPageFollowingRequest(
      `?page=${pageNo}&size=${size}&sortOrder=${sortOrder}`,
    )()()();
};
