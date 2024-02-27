import { z } from 'zod';

export const myPageSpotListSchema = z.object({
  spot_id: z.number().int(),
  title: z.string(),
  isSecret: z.boolean(),
  image_url: z.string(),
  post_date: z.string(),
  rating: z.number(),
});
