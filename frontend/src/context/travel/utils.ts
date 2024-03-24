import { TInputImage } from '@/app/_components/images';
import {
  defaultChoosePlace,
  type TChoosePlace,
  type TCoords,
  type TCourseState,
  type TPlaceState,
  type TReview,
  type TSpotState,
} from './schema';

export function isEqualArray<T>(
  first: T[],
  second: T[],
  isEqual: (first: T, second: T) => boolean,
) {
  const sortedFirst = first.toSorted();
  const sortedSecond = second.toSorted();

  return sortedFirst.every((value, index) =>
    isEqual(value, sortedSecond[index]),
  );
}

export function isEqualCoords(first: TCoords, second: TCoords) {
  const results = [first.lat === second.lat, first.lng === second.lng];

  return results.every((result) => result);
}

// 지도 이미지는 다를 수 있다고 생각(백엔드 / 네이버)
export function isEqualPlace(first: TChoosePlace, second: TChoosePlace) {
  const results = [
    first.name === second.name,
    first.address === second.address,
    isEqualCoords(first.coords, second.coords),
  ];

  return results.every((result) => result);
}

export function isEqualReview(first: TReview, second: TReview) {
  const results = [
    first.title === second.title,
    first.content === second.content,
    first.rate === second.rate,
  ];

  return results.every((result) => result);
}

export function isEqualImage(first: TInputImage, second: TInputImage) {
  const results = [
    first.filename === second.filename,
    first.uri === second.uri,
  ];

  return results.every((result) => result);
}

export function isEqualSpot(first: TSpotState, second: TSpotState) {
  const firstImages = first.images;
  const secondImages = second.images;

  const isEqualImages =
    firstImages.type === 'new' && secondImages.type === 'new'
      ? isEqualArray(firstImages.data, secondImages.data, isEqualImage)
      : firstImages.type === 'exist' && secondImages.type === 'exist'
      ? isEqualArray(
          firstImages.data,
          secondImages.data,
          (first, second) => first === second,
        )
      : false;

  const results = [
    isEqualPlace(first.place, second.place),
    isEqualImages,
    isEqualReview(first.review, second.review),
  ];

  return results.every((result) => result);
}

export function isEqualCourse(first: TCourseState, second: TCourseState) {
  const results = [
    isEqualArray(first.spots, second.spots, isEqualSpot),
    isEqualReview(first.review, second.review),
  ];

  return results.every((result) => result);
}

export function isDefaultChoosePlace(choosePlace: TChoosePlace) {
  return isEqualPlace(choosePlace, defaultChoosePlace);
}

export const defaultPlaceState: TPlaceState = {
  type: 'spot',
  data: defaultChoosePlace,
};

export function isDefaultPlace(state: TPlaceState) {
  if (state.type === 'spot' && defaultPlaceState.type === 'spot') {
    return isEqualPlace(state.data, defaultPlaceState.data);
  }

  if (state.type === 'course') {
    return state.data.length === 0;
  }

  return false;
}
