import { z } from 'zod';

export const backendErrorSchema = z.object({
  code: z.number(),
  message: z.string(),
});

export const postSuccessResponseSchema = z.object({
  message: z.string(),
});

export type TBackendError = z.infer<typeof backendErrorSchema>;

export const placeSchema = z.object({
  id: z.number().int(),
  place_name: z.string(),
  review_count: z.string(),
  thumbnail_image_url: z.string(),
  rate: z.string(),
  bookmarked: z.boolean(),
});

export type TPlace = z.infer<typeof placeSchema>;

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

export const mySpotForPlaceSchema = z.object({
  exists: z.boolean(),
  rate: z.string(),
  image_urls: z.array(z.string()),
  create_date: z.coerce.date(),
  description: z.string(),
});

export type TMySpotForPlace = z.infer<typeof mySpotForPlaceSchema>;

export type TMyInfo = z.infer<typeof myInfoSchema>;

export const regionSchema = z.object({
  id: z.number().int(),
  name: z.string(),
});

export type TRegion = z.infer<typeof regionSchema>;
