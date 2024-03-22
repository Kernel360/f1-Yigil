'use server';

import { z } from 'zod';

import { cookies } from 'next/headers';
import { getBaseUrl } from '@/app/utilActions';
import { myPageSpotListSchema } from '@/types/myPageResponse';
import {
  backendErrorSchema,
  existingSpotsSchema,
  naverStaticMapUrlErrorSchema,
  postResponseSchema,
} from '@/types/response';

import type { TExistingSpots } from '@/types/response';
import type {
  TCourseState,
  TLineString,
  TSpotState,
} from '@/context/travel/schema';
import {
  blobTodataUrl,
  coordsToGeoJSONPoint,
  dataUrlToBlob,
  getMIMETypeFromDataURI,
} from '@/utils';

type TMyPageSpotList = z.infer<typeof myPageSpotListSchema>;

async function fetchMySpots(pageNo: number, size: number, sortOrder: string) {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const endpoint = `${BASE_URL}/v1/spots/my`;
  const queryParams = Object.entries({
    page: pageNo,
    size,
    sortBy:
      sortOrder !== 'rate'
        ? `created_at&sortOrder=${sortOrder}`
        : `rate&sortOrder=desc`,
  })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const response = await fetch(`${endpoint}?${queryParams}`, {
    headers: {
      Cookie: `SESSION=${cookie}`,
    },
  });

  return await response.json();
}

export async function getMySpots(
  pageNo: number = 1,
  size: number = 5,
  sortOrder: string = 'desc',
): Promise<
  | { status: 'failed'; message: string }
  | { status: 'succeed'; data: TMyPageSpotList }
> {
  try {
    const json = await fetchMySpots(pageNo, size, sortOrder);

    const error = backendErrorSchema.safeParse(json);

    if (error.success) {
      console.error(`${error.data.code} - ${error.data.message}`);
      throw new Error(`${error.data.code} - ${error.data.message}`);
    }

    const result = myPageSpotListSchema.safeParse(json);

    if (!result.success) {
      console.log(result.error.message);
      throw new Error('알 수 없는 에러입니다!');
    }

    return { status: 'succeed', data: result.data };
  } catch (error) {
    if (error instanceof Error) {
      console.error(error.message);
      return { status: 'failed', message: error.message };
    }

    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }
}

export async function getSelectedSpots(
  spotIds: number[],
): Promise<
  | { status: 'failed'; message: string; code?: number }
  | { status: 'succeed'; data: TExistingSpots }
> {
  const BASE_URL = await getBaseUrl();
  const cookie = cookies().get('SESSION')?.value;

  const response = await fetch(`${BASE_URL}/v1/courses/spots`, {
    method: 'POST',
    body: JSON.stringify({ spot_ids: spotIds }),
    headers: {
      'Content-Type': 'application/json',
      Cookie: `SESSION=${cookie}`,
    },
  });

  const json = await response.json();

  const backendError = backendErrorSchema.safeParse(json);

  if (backendError.success) {
    const { message, code } = backendError.data;
    return { status: 'failed', message, code };
  }

  const result = existingSpotsSchema.safeParse(json);

  if (!result.success) {
    return { status: 'failed', message: '알 수 없는 에러입니다!' };
  }

  return { status: 'succeed', data: result.data };
}

function routeUrl(coords: { lng: number; lat: number }[]) {
  const endpoint =
    'https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving';

  const start = `${coords[0].lng},${coords[0].lat}`;
  const goal = `${coords[coords.length - 1].lng},${
    coords[coords.length - 1].lat
  }`;

  const waypointsCoords =
    coords.length > 2 ? coords.slice(1, coords.length - 1) : [];

  const waypoints = waypointsCoords
    .map((waypoint) => `${waypoint.lng},${waypoint.lat}`)
    .join(':');

  const queryParams = Object.entries({ start, goal, waypoints })
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  return `${endpoint}?${queryParams}`;
}

const directionsErrorSchema = z.object({
  error: z.object({
    errorCode: z.string(),
    message: z.string(),
  }),
});

/**
 * Array<[lng / lat]>
 */
const routeUnitSchema = z.object({
  path: z.array(z.array(z.number()).length(2)),
});

const routeSchema = z.object({
  traoptimal: z.array(routeUnitSchema),
});

const resultCode = {
  SAME: 1,
  START_GOAL_NOT_ROAD: 2,
  UNABLE: 3,
  WAYPOINT_NOT_ROAD: 4,
  TOO_LONG: 5,
} as const;

const directionsSuccessSchema = z.discriminatedUnion('code', [
  z.object({ code: z.literal(0), route: routeSchema }),
  z.object({ code: z.nativeEnum(resultCode) }),
]);

type TRouteGeoJsonResult =
  | { status: 'failed'; code: number; message: string }
  | { status: 'success'; data: TLineString };

export async function getRouteGeoJson(
  spots: TSpotState[],
): Promise<TRouteGeoJsonResult> {
  const coords = spots.map(({ place }) => place.coords);

  const response = await fetch(routeUrl(coords), {
    method: 'GET',
    headers: {
      'X-NCP-APIGW-API-KEY-ID': process.env.NAVER_MAPS_CLIENT_ID,
      'X-NCP-APIGW-API-KEY': process.env.MAP_SECRET,
    },
  });

  const json = await response.json();

  if (!response.ok) {
    const error = directionsErrorSchema.safeParse(json);

    if (!error.success) {
      console.error(error.error.message);
      return {
        status: 'failed',
        code: response.status,
        message: '알 수 없는 에러입니다!',
      };
    }

    const errorData = error.data.error;

    return {
      status: 'failed',
      code: Number.parseInt(errorData.errorCode),
      message: errorData.message,
    };
  }

  const result = directionsSuccessSchema.safeParse(json);

  if (!result.success) {
    console.error(result.error.message);
    return {
      status: 'failed',
      code: response.status,
      message: '알 수 없는 에러입니다!',
    };
  }

  if (!(result.data.code === 0)) {
    console.error(result.data.code);

    let drivingError: { status: 'failed'; code: number; message: string } = {
      status: 'failed',
      code: result.data.code,
      message: '',
    };

    switch (result.data.code) {
      case 1: {
        drivingError = {
          ...drivingError,
          message: '출발지와 도착지가 동일합니다!',
        };
        break;
      }
      case 2: {
        drivingError = {
          ...drivingError,
          message: '출발지 또는 도착지가 도로 주변이 아닙니다!',
        };
        break;
      }
      case 3: {
        drivingError = {
          ...drivingError,
          message: '자동차 길찾기 결과를 제공할 수 없습니다!',
        };
        break;
      }
      case 4: {
        drivingError = {
          ...drivingError,
          message: '경유지가 도로 주변이 아닙니다!',
        };
        break;
      }
      case 5: {
        drivingError = { ...drivingError, message: '요청 경로가 너무 깁니다!' };
        break;
      }
    }

    return drivingError;
  }

  const data = result.data.route.traoptimal;

  const geoJson: TLineString = {
    type: 'LineString',
    coordinates: data[0].path,
  };

  return { status: 'success', data: geoJson };
}

function staticMapUrlWithMarker(
  width: number,
  height: number,
  coords: {
    lat: number;
    lng: number;
  }[],
) {
  const colors = ['0x60A5FA', '0x3B82F6', '0x2564EB', '0x1D4FD8', '0x1E40AF'];

  const markers = coords.map(({ lng, lat }, index) => {
    return {
      type: 'n',
      pos: `${lng} ${lat}`,
      label: index + 1,
      color: colors[index],
    };
  });

  const markerOptions = markers
    .map((marker) =>
      Object.entries(marker)
        .map(([key, value]) => `${key}:${value}`)
        .join('|'),
    )
    .map((options) => `markers=${options}`)
    .join('&');

  const endpoint = 'https://naveropenapi.apigw.ntruss.com/map-static/v2/raster';
  const queryParams = `w=${width}&h=${height}&format=png&${markerOptions}&scale=2`;

  return `${endpoint}?${queryParams}`;
}

export async function getCourseStaticMap(
  coords: { lng: number; lat: number }[],
  width: number = 300,
  height: number = 200,
): Promise<
  { status: 'failed'; message: string } | { status: 'success'; dataUrl: string }
> {
  const url = staticMapUrlWithMarker(width, height, coords);

  const response = await fetch(url, {
    method: 'GET',
    headers: {
      'X-NCP-APIGW-API-KEY-ID': process.env.NAVER_MAPS_CLIENT_ID,
      'X-NCP-APIGW-API-KEY': process.env.MAP_SECRET,
    },
    cache: 'no-store',
  });

  if (!response.ok) {
    const json = await response.json();

    console.log(json);

    const error = naverStaticMapUrlErrorSchema.safeParse(json.error);

    if (!error.success) {
      console.error(error.error.message);
      return { status: 'failed', message: '알 수 없는 에러입니다!' };
    }

    return {
      status: 'failed',
      message: '검색 엔진으로부터 지도를 받아올 수 없습니다!',
    };
  }

  const image = await response.blob();
  const dataUrl = await blobTodataUrl(image);

  return { status: 'success', dataUrl };
}

async function parseAddCourseState(course: TCourseState): Promise<FormData> {
  const formData = new FormData();

  const { spots, review, staticMapImageUrl, lineString } = course;

  formData.append('title', review.title || '');
  formData.append('description', review.content);
  formData.append('rate', review.rate.toString());
  formData.append('isPrivate', JSON.stringify(false));
  formData.append('lineStringJson', JSON.stringify(lineString));
  formData.append('representativeSpotOrder', JSON.stringify(1));

  const spotRegisterRequests = spots.map((spot) => {
    const place = spot.place;
    const review = spot.review;
    const pointJson = coordsToGeoJSONPoint(place.coords);

    return {
      title: 'Spot Review',
      placeName: place.name,
      placeAddress: place.address,
      rate: review.rate,
      description: review.content,
      pointJson,
      placePointJson: pointJson,
    };
  });

  spotRegisterRequests.forEach((request, index) => {
    Object.entries(request).forEach(([key, value]) =>
      formData.append(
        `spotRegisterRequests[${index}].${key}`,
        typeof value === 'number' ? value.toString() : value,
      ),
    );
  });

  formData.append(
    'mapStaticImageFile',
    new File(
      [dataUrlToBlob(staticMapImageUrl)],
      `${review.title} 코스 이미지.png`,
      {
        type: 'image/png',
      },
    ),
  );

  const spotImages = spots.map((spot) => {
    const images = spot.images;

    return images.type === 'new'
      ? images.data.map(
          ({ filename, uri }) =>
            new File([dataUrlToBlob(uri)], filename, {
              type: getMIMETypeFromDataURI(uri),
            }),
        )
      : [];
  });

  spots.forEach((spot, index) => {
    const { name, mapImageUrl } = spot.place;

    if (mapImageUrl.startsWith('data:')) {
      const staticMapFile = new File(
        [dataUrlToBlob(mapImageUrl)],
        `${name} 지도 이미지.png`,
        {
          type: 'image/png',
        },
      );

      formData.append(
        `spotRegisterRequests[${index}].mapStaticImageFile`,
        staticMapFile,
      );
      formData.append(
        `spotRegisterRequests[${index}].placeImageFile`,
        staticMapFile,
      );
    }
  });

  spotImages.forEach((images, spotIndex) => {
    images.forEach((image, imageIndex) => {
      formData.append(
        `spotRegisterRequests[${spotIndex}].files[${imageIndex}]`,
        image,
      );
    });
  });

  return formData;
}

export async function postCourse(
  course: TCourseState,
): Promise<
  { status: 'succeed' } | { status: 'failed'; message: string; code: number }
> {
  const BASE_URL = await getBaseUrl();
  const session = cookies().get('SESSION')?.value;

  const formData = await parseAddCourseState(course);

  const response = await fetch(`${BASE_URL}/v1/courses`, {
    method: 'POST',
    body: formData,
    headers: {
      Cookie: `SESSION=${session}`,
    },
  });

  if (response.status.toString()[0] === '5') {
    return {
      status: 'failed',
      message: '서버와 연결할 수 없습니다!',
      code: response.status,
    };
  }

  const json = await response.json();

  const error = backendErrorSchema.safeParse(json);

  if (error.success) {
    const { code, message } = error.data;
    console.error(`${code} - ${message}`);
    return { status: 'failed', message, code };
  }

  const result = postResponseSchema.safeParse(json);

  if (!result.success) {
    return { status: 'failed', message: '알 수 없는 에러입니다!', code: 500 };
  }

  return { status: 'succeed' };
}
