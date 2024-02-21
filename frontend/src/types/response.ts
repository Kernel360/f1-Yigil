import { z } from 'zod';

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

export type TRegion = z.infer<typeof regionSchema>;

export const placeSchema = z.object({
  id: z.number().int(),
  region: regionSchema,
  name: z.string(),
  image_url: z.string(),
  liked: z.boolean().optional(),
  review_count: z.number().int(),
  liked_count: z.number().int(),
  rating: z.number(),
});

export type TPlace = z.infer<typeof placeSchema>;

export const placesSchema = z.array(placeSchema);

export const placeDetailSchema = z.object({
  id: z.number().int(),
  name: z.string(),
  address: z.string(),
  image_url: z.string(),
  map_image_url: z.string(),
  liked: z.boolean().optional(),
  review_count: z.number().int(),
  liked_count: z.number().int(),
  rating: z.number(),
});

export type TPlaceDetail = z.infer<typeof placeDetailSchema>;
