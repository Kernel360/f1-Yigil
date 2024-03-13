import { ZodTypeAny, z } from 'zod';

import { inputImageSchema } from '@/app/_components/images';

const IMAGES_COUNT = 5;
const PLACES_COUNT = 5;
export const SPOTS_COUNT = 5;

export const coordsSchema = z.object({ lng: z.number(), lat: z.number() });
export type TCoords = z.infer<typeof coordsSchema>;

export const choosePlaceSchema = z.object({
  name: z.string(),
  address: z.string(),
  mapImageUrl: z.string(),
  coords: coordsSchema,
});
export type TChoosePlace = z.infer<typeof choosePlaceSchema>;

export const manyChoosePlaceSchema = z
  .array(choosePlaceSchema)
  .min(0)
  .max(PLACES_COUNT);

export const reviewSchema = z.object({
  rate: z.number().int().gte(1).lte(5),
  title: z.string().optional(),
  content: z.string().max(30),
});
export type TReview = z.infer<typeof reviewSchema>;

export const manyInputImageSchema = z
  .array(inputImageSchema)
  .min(0)
  .max(IMAGES_COUNT);

export const spotStateSchema = z.object({
  place: choosePlaceSchema,
  images: manyInputImageSchema,
  review: reviewSchema,
});
export type TSpotState = z.infer<typeof spotStateSchema>;

export const manySpotStateSchema = z.array(spotStateSchema);

export const courseStateSchema = z.object({
  spots: manySpotStateSchema.min(0).max(SPOTS_COUNT),
  review: reviewSchema,
});
export type TCourseState = z.infer<typeof courseStateSchema>;

const currentSpotDataSchema = <T extends ZodTypeAny>(schema: T) =>
  z.object({
    data: schema,
    index: z.number().int().gte(0).lt(SPOTS_COUNT),
  });

export const currentSpotReviewSchema = currentSpotDataSchema(reviewSchema);
export const currentSpotImagesSchema =
  currentSpotDataSchema(manyInputImageSchema);
export const currentSpotPlaceSchema = currentSpotDataSchema(choosePlaceSchema);
