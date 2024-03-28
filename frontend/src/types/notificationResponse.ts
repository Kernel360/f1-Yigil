import z from 'zod';

const notificationSchema = z.object({
  notification_id: z.number(),
  sender_profile_image_url: z.string(),
  sender_id: z.number(),
  message: z.string(),
  create_date: z.coerce
    .date()
    .transform((date) => date.toLocaleDateString('ko-kr')),
  read: z.boolean(),
});

export const notificationResponseSchema = z.object({
  has_next: z.boolean(),
  notifications: z.array(notificationSchema),
});

export type TNotification = z.infer<typeof notificationResponseSchema>;
