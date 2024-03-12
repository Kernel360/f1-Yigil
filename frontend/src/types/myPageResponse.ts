import { z } from 'zod';

export const myPageSpotItemSchema = z.object({
  spot_id: z.number().int(),
  title: z.string(),
  is_private: z.boolean(),
  image_url: z.string(),
  created_date: z.coerce.date().transform((date) => date.toLocaleDateString()),
  rate: z.number(),
});

export type TMyPageSpot = z.infer<typeof myPageSpotItemSchema>;

export const myPageSpotListSchema = z.object({
  content: z.array(myPageSpotItemSchema),
  total_pages: z.number(),
});

export type TMyPageBookmark = z.infer<typeof myPageBookmarkItemSchema>;
export const myPageBookmarkItemSchema = z.object({
  place_id: z.number().int(),
  place_name: z.string(),
  place_image: z.string(),
  rate: z.number(),
});

export const myPageBookmarkListSchema = z.object({
  bookmarks: z.array(myPageBookmarkItemSchema),
  has_next: z.boolean(),
});

export type TMyPageCourse = z.infer<typeof myPageCourseItemSchema>;

export const myPageCourseItemSchema = z.object({
  course_id: z.number().int(),
  title: z.string(),
  map_static_image_url: z.string(),
  is_private: z.boolean(),
  created_date: z.coerce.date().transform((date) => date.toLocaleDateString()),
  rate: z.number(),
  spot_count: z.number(),
});
export const myPageCourseListSchema = z.object({
  content: z.array(myPageCourseItemSchema),
  total_pages: z.number(),
});
