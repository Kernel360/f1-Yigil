import { z } from 'zod';

export const myPageSpotListSchema = z.array(
  z.object({
    spot_id: z.number().int(),
    title: z.string(),
    is_private: z.boolean(),
    image_url: z.string(),
    created_date: z.string(),
    rate: z.number(),
  }),
);

export const myPageCourseListSchema = z.array(
  z.object({
    course_id: z.number().int(),
    title: z.string(),
    map_static_image_url: z.string(),
    is_private: z.boolean(),
    created_date: z.string(),
    rate: z.number(),
    spot_count: z.number(),
  }),
);

export const myPageBookmarkListSchema = z.array(
  z.object({
    place_id: z.number().int(),
    place_name: z.string(),
    place_image: z.string(),
    rate: z.number(),
    is_bookmarked: z.boolean(),
  }),
);
