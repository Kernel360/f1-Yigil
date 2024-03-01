import { z } from 'zod';

export const backendErrorSchema = z.object({
  code: z.number(),
  message: z.string(),
});

export type TBackendError = z.infer<typeof backendErrorSchema>;

const REGION_VALUES = [
  '서울',
  '경기',
  '인천',
  '강원',
  '대전',
  '충남',
  '충북',
  '세종',
  '광주',
  '전남',
  '전북',
  '부산',
  '대구',
  '울산',
  '경남',
  '경북',
  '제주',
] as const;

export const regionSchema = z.enum(REGION_VALUES);

export const placeSchema = z.object({
  id: z.number().int(),
  place_name: z.string(),
  review_count: z.string(),
  thumbnail_image_url: z.string(),
  rate: z.string(),
  bookmarked: z.boolean(),
});

export type TPlace = z.infer<typeof placeSchema>;

export const placesSchema = z.array(placeSchema);

export const placeDetailSchema = z.object({
  id: z.number().int(),
  place_name: z.string(),
  address: z.string(),
  thumbnail_image_url: z.string(),
  map_static_image_url: z.string(),
  bookmarked: z.boolean(),
  review_count: z.number().int(),
  rate: z.number(),
});

export type TPlaceDetail = z.infer<typeof placeDetailSchema>;

export const searchItemSchema = z.object({
  title: z.string(),
  roadAddress: z.string(),
  mapx: z.string(),
  mapy: z.string(),
});

export const searchItemsSchema = z.array(searchItemSchema);

export const datumWithAddressSchema = z.object({
  x: z.string(),
  y: z.string(),
});

export const dataWithAddressSchema = z.array(datumWithAddressSchema);

export const staticMapUrlSchema = z.object({
  exists: z.boolean(),
  map_static_image_url: z.string().optional(),
});

export const naverStaticMapUrlErrorSchema = z.object({
  errorCode: z.string(),
  message: z.string(),
});

export const postSpotResponseSchema = z.object({
  message: z.string(),
});

export type TPostSpotSuccess = z.infer<typeof postSpotResponseSchema>;

export const myInfoSchema = z.object({
  member_id: z.number().int(),
  email: z.string().email(),
  nickname: z.string(),
  profile_image_url: z.string().url(),
  following_count: z.number().int(),
  follower_count: z.number().int(),
});
