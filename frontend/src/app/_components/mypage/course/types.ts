import { TLineString } from '@/context/travel/schema';

export interface TPatchCourse {
  title: string;
  description: string;
  rate: string;
  spotIdOrder?: number[];
  line_string_json?: TLineString;
  map_static_image_url?: string;
  spots?: TSpotPatchCourse[];
}

export interface TSpotPatchCourse {
  id: number;
  place_name?: string;
  place_address?: string;
  create_date?: string;
  order?: string;
  description: string;
  rate: string;
  image_url_list: { filename: string; uri: string }[];
}
