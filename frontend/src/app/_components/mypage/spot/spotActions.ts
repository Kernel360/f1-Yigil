'use server';

import { requestWithCookie } from '../../api/httpRequest';

export const myPageSpotRequest = requestWithCookie('spots/my');

export const getMyPageSpots = async (
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
  selectOption: string = 'all',
) => {
  return myPageSpotRequest(
    `?page=${pageNo}&size=${size}&sortOrder=${
      sortOrder !== 'rate' ? sortOrder : `sortOrder=desc&sortBy=rate`
    }&selected=${selectOption}`,
  )()()();
};

export const deleteMyPageSpot = (spotId: number) => {
  return myPageSpotRequest(`${spotId}`)('DELETE')()();
};
