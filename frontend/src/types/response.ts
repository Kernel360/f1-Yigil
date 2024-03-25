import { ZodType, ZodTypeDef, z } from 'zod';

interface TBackendRequestFailed {
  status: 'failed';
  message: string;
  code?: number;
}
interface TBackendRequestSucceed<T> {
  status: 'succeed';
  data: T;
}
export type TBackendRequestResult<T> =
  | TBackendRequestFailed
  | TBackendRequestSucceed<T>;

export const postResponseSchema = z.object({
  message: z.string(),
});

export const backendErrorSchema = z.object({
  code: z.number(),
  message: z.string(),
});

export const fetchableSchema = <TOutput, TInput>(
  schema: ZodType<TOutput, ZodTypeDef, TInput>,
) =>
  z.object({
    content: z.array(schema),
    has_next: z.boolean(),
  });

export type TBackendError = z.infer<typeof backendErrorSchema>;

export const placeSchema = z.object({
  id: z.number().int(),
  place_name: z.string(),
  review_count: z.string(),
  thumbnail_image_url: z.string(),
  rate: z.string(),
  bookmarked: z.boolean(),
});

export type TPlace = z.infer<typeof placeSchema>;

export const courseSchema = z.object({
  id: z.number().int(),
  title: z.string(),
  map_static_image_url: z.string(),
  owner_profile_image_url: z.string(),
  owner_nickname: z.string(),
  spot_count: z.number().int(),
  rate: z.number(),
  liked: z.boolean(),
  create_date: z.coerce.date().transform((date) => date.toLocaleDateString()),
});

export type TCourse = z.infer<typeof courseSchema>;

export const placeDetailSchema = z.object({
  id: z.number().int(),
  place_name: z.string(),
  address: z.string(),
  thumbnail_image_url: z.string(),
  map_static_image_url: z.string(),
  bookmarked: z.boolean(),
  review_count: z.number().int(),
  rate: z.number(),
});

export type TPlaceDetail = z.infer<typeof placeDetailSchema>;

export const searchItemSchema = z.object({
  title: z.string(),
  roadAddress: z.string(),
  mapx: z.string(),
  mapy: z.string(),
});

export const searchItemsSchema = z.array(searchItemSchema);

export const datumWithAddressSchema = z.object({
  x: z.string(),
  y: z.string(),
});

export const dataWithAddressSchema = z.array(datumWithAddressSchema);

export const staticMapUrlSchema = z.object({
  exists: z.boolean(),
  map_static_image_url: z.string().optional(),
  registered_place: z.boolean(),
});

export const naverStaticMapUrlErrorSchema = z.object({
  errorCode: z.string(),
  message: z.string(),
});

export const postSpotResponseSchema = z.object({
  message: z.string(),
});

export type TPostSpotSuccess = z.infer<typeof postSpotResponseSchema>;

export const myInfoSchema = z.object({
  member_id: z.number().int(),
  email: z.string().email(),
  nickname: z.string(),
  profile_image_url: z.string(),
  favorite_regions: z.array(z.object({ id: z.number(), name: z.string() })),
  following_count: z.number().int(),
  follower_count: z.number().int(),
  ages: z.string(),
  gender: z.string(),
});

export const mySpotForPlaceSchema = z.object({
  exists: z.boolean(),
  rate: z.string(),
  image_urls: z.array(z.string()),
  create_date: z.coerce.date(),
  description: z.string(),
});

export type TMySpotForPlace = z.infer<typeof mySpotForPlaceSchema>;

export type TMyInfo = z.infer<typeof myInfoSchema>;

export const TMapPlaceSchema = z.object({
  id: z.number().int(),
  place_name: z.string(),
  x: z.number(),
  y: z.number(),
});

export const TMapPlacesSchema = z.object({
  places: z.array(TMapPlaceSchema),
  total_pages: z.number(),
  current_page: z.number(),
});

export type TMapPlace = z.infer<typeof TMapPlaceSchema>;

export const regionSchema = z.object({
  id: z.number().int(),
  name: z.string(),
});

export type TRegion = z.infer<typeof regionSchema>;

export const spotSchema = z.object({
  id: z.number().int(),
  image_url_list: z.array(z.string()),
  description: z.string(),
  owner_profile_image_url: z.string(),
  owner_nickname: z.string(),
  liked: z.boolean(),
  rate: z.string(),
  create_date: z.coerce.date(),
});

export type TSpot = z.infer<typeof spotSchema>;

export const commentSchema = z.object({
  id: z.number().int(),
  content: z.string(),
  member_id: z.number().int(),
  member_nickname: z.string(),
  member_image_url: z.string(),
  child_count: z.number().int(),
  created_at: z.coerce.date(),
});

export type TComment = z.infer<typeof commentSchema>;

export const keywordsSchema = z.object({
  keywords: z.array(z.string()),
});

export const existingSpotSchema = z.object({
  spot_id: z.number().int(),
  place_name: z.string(),
  place_address: z.string(),
  rate: z.number(),
  description: z.string(),
  image_urls: z.array(z.string()),
  create_date: z.coerce
    .date()
    .transform((date) => date.toLocaleDateString('ko-kr')),
  point: z.object({
    x: z.number(),
    y: z.number(),
  }),
});

export const existingSpotsSchema = z.object({
  spot_details: z.array(existingSpotSchema),
});

export type TExistingSpot = z.infer<typeof existingSpotSchema>;
export type TExistingSpots = z.infer<typeof existingSpotsSchema>;
