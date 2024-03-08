import { getBaseUrl } from '@/app/utilActions';
import { TMapPlacesSchema } from '@/types/response';

export const getNearPlaces = async (
  bounds: {
    maxX: number;
    maxY: number;
    minX: number;
    minY: number;
  },
  page: number,
) => {
  const BASE_URL = await getBaseUrl();
  const { maxX, maxY, minX, minY } = bounds;
  const res = await fetch(
    `${BASE_URL}/v1/places/near?minX=${minX}&minY=${minY}&maxX=${maxX}&maxY=${maxY}&page=${page}`,
  );
  const places = await res.json();
  const parsedPlaces = TMapPlacesSchema.safeParse(places);
  return parsedPlaces;
};
