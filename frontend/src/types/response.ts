import { z } from 'zod';

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
