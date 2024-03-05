import { TMapPlacesSchema } from '@/types/response';

const { BASE_URL, DEV_BASE_URL, ENVIRONMENT } = process.env;

const baseUrl =
  process.env.ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;
export const getNearPlaces = async (
  bounds: {
    maxX: number;
    maxY: number;
    minX: number;
    minY: number;
  },
  page: number,
) => {
  const { maxX, maxY, minX, minY } = bounds;
  const res = await fetch(
    `http://3.34.236.45:8080/api/v1/places/near?minX=${minX}&minY=${minY}&maxX=${maxX}&maxY=${maxY}&page=${page}`,
  );
  const places = await res.json();
  const parsedPlaces = TMapPlacesSchema.safeParse(places);
  return parsedPlaces;
};
