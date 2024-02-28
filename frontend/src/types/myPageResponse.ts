import { z } from 'zod';

export const myPageSpotListSchema = z.array(
  z.object({
    spot_id: z.number().int(),
    title: z.string(),
    isSecret: z.boolean(),
    image_url: z.string(),
    post_date: z.string(),
    rating: z.number(),
  }),
);

export const myPageCourseListSchema = z.array(
  z.object({
    course_id: z.number().int(),
    title: z.string(),
    static_image_url: z.string(),
    isSecret: z.boolean(),
    created_at: z.string(),
    rate: z.number(),
    spot_count: z.number(),
  }),
);
