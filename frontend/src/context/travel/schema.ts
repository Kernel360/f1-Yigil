import { z } from 'zod';

import { inputImageSchema } from '@/app/_components/images';

export const choosePlaceSchema = z.object({
  name: z.string(),
  address: z.string(),
  mapImageUrl: z.string(),
  coords: z.object({ lng: z.number(), lat: z.number() }),
});
export type TChoosePlace = z.infer<typeof choosePlaceSchema>;

export const reviewSchema = z.object({
  rate: z.number().int().gte(1).lte(5),
  title: z.string().optional(),
  content: z.string().max(30),
});
export type TReview = z.infer<typeof reviewSchema>;

export const inputImagesSchema = z.array(inputImageSchema);

export const spotStateSchema = z.object({
  place: choosePlaceSchema,
  images: inputImagesSchema,
  review: reviewSchema,
});
export type TSpotState = z.infer<typeof spotStateSchema>;

export const courseStateSchema = z.object({
  spots: z.array(spotStateSchema).min(2).max(5),
  courseReview: reviewSchema,
});
export type TCourseState = z.infer<typeof courseStateSchema>;
