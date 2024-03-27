import { z } from 'zod';
import { backendErrorSchema } from './types/response';

import type { ZodType, ZodTypeDef } from 'zod';
import type { TBackendRequestResult } from './types/response';

export function dataUrlToBlob(dataURI: string) {
  const byteString = atob(dataURI.split(',')[1]);

  // separate out the mime component
  const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

  // write the bytes of the string to an ArrayBuffer
  const ab = new ArrayBuffer(byteString.length);
  const ia = new Uint8Array(ab);
  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ab], { type: mimeString });
}

export async function blobTodataUrl(blob: Blob) {
  const buffer = Buffer.from(await blob.arrayBuffer());

  return `data:${blob.type};base64,${buffer.toString('base64')}`;
}

export function getMIMETypeFromDataURI(dataURI: string) {
  const dataType = dataURI.split(',')[0];
  const types = dataType.split(':')[1];

  return types.split(';')[0];
}

export function coordsToGeoJSONPoint(coords: { lat: number; lng: number }) {
  return JSON.stringify({
    type: 'Point',
    coordinates: [coords.lng, coords.lat],
  });
}

export function parseSearchHistory(historyStr: string | null) {
  const historiesSchema = z.array(z.string());

  if (historyStr === null) {
    return [];
  }

  const json = JSON.parse(historyStr);

  const result = historiesSchema.safeParse(json);

  if (!result.success) {
    return [];
  }

  return result.data;
}

export const scrollToTop = () => {
  if (typeof window !== 'undefined') window.scrollTo(0, 0);
};

export const cdnPathToRelativePath = (path: string) => {
  return path.split('http://cdn.yigil.co.kr/')[1];
};

export function checkBatchimEnding(word: string) {
  const lastLetter = word[word.length - 1];
  const uni = lastLetter.charCodeAt(0);

  if (uni < 44032 || uni > 55203) return false;

  return (uni - 44032) % 28 != 0;
}

export function parseResult<TOutput, TInput>(
  schema: ZodType<TOutput, ZodTypeDef, TInput>,
  json: unknown,
): TBackendRequestResult<TOutput> {
  const error = backendErrorSchema.safeParse(json);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const result = schema.safeParse(json);

  if (!result.success) {
    console.error('알 수 없는 에러입니다!');
    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }

  return { status: 'succeed', data: result.data };
}
