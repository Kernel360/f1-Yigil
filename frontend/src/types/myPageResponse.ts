import { z } from 'zod';

export const myPageSpotListSchema = z.object({
  spot_id: z.number().int(),
  title: z.string(),
  isSecret: z.boolean(),
  image_url: z.string(),
  post_date: z.string(),
  rating: z.number(),
});

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

export const myPageBookmarkListSchema = z.array(
  z.object({
    place_id: z.number().int(),
    place_name: z.string(),
    place_image_url: z.string(),
    rate: z.number(),
    is_bookmarked: z.boolean(),
  }),
);
