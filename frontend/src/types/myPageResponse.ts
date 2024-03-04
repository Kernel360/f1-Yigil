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

export const myPageFollowSchema = z.object({
  member_id: z.number().int(),
  nickname: z.string(),
  profile_image_url: z.string(),
});

export type TMyPageFollow = z.infer<typeof myPageFollowSchema>;

export const myPageFollowListSchema = z.object({
  content: z.array(myPageFollowSchema),
  has_next: z.boolean(),
});
