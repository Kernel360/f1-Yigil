import { z } from 'zod';

import ImageHandler from './ImageHandler';

export const inputImageSchema = z.object({
  filename: z.string(),
  uri: z.string(),
});

export type TInputImage = z.infer<typeof inputImageSchema>;

export default ImageHandler;
