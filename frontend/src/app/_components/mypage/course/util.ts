import { TMyPageCourseDetail } from '@/types/myPageResponse';
import { TModifyCourse } from './CourseDetail';
import { TPatchCourse, TSpotPatchCourse } from './types';

export function checkCourseDifference(
  origin: TMyPageCourseDetail,
  willCompare: TModifyCourse,
) {
  const differences: TPatchCourse = {
    title: '',
    description: '',
    rate: '',
  };

  const differentSpots = checkSpotsDifference(origin.spots, willCompare.spots);
  const hasDifferentSpots = differentSpots.length;
  if (hasDifferentSpots)
    differences.spots = differentSpots as TSpotPatchCourse[];

  differences.title = compareDifferentTitle(origin.title, willCompare.title);

  differences.description = compareDifferentDesc(
    origin.description,
    willCompare.description,
  );

  differences.rate = compareDifferentRate(origin.rate, willCompare.rate);

  const originIdOrder = origin.spots.map((spot) => spot.id);

  if (isDifferentIdOrders(originIdOrder, willCompare.spotIdOrder)) {
    differences.line_string_json = willCompare.line_string_json;
    differences.map_static_image_url = willCompare.map_static_image_url;
    differences.spotIdOrder = willCompare.spotIdOrder;
  } else {
    differences.line_string_json = origin.line_string_json;
    differences.spotIdOrder = originIdOrder;
  }

  const result = [
    isDifferentTitle(origin.title, willCompare.title),
    isDifferentDesc(origin.description, willCompare.description),
    isDifferentRate(origin.rate, willCompare.rate),
    isDifferentIdOrders(originIdOrder, willCompare.spotIdOrder),
  ];

  const isDifferentCourse = result.some((item) => item);

  return isDifferentCourse || hasDifferentSpots ? differences : null;
}

function checkSpotsDifference(
  origin: TMyPageCourseDetail['spots'],
  willCompare: TModifyCourse['spots'],
) {
  const spotDifferences = origin.map((originSpot, index) => {
    const willCompareSpot = willCompare.find(
      (compare) => compare.id === originSpot.id,
    );
    if (willCompareSpot) {
      const result = [
        isDifferentDesc(originSpot.description, willCompareSpot.description),
        isDifferentRate(originSpot.rate, willCompareSpot.rate),
        isDifferentImageUrl(
          originSpot.image_url_list,
          willCompareSpot.image_url_list,
        ),
      ];

      const isSpotDifferent = result.some((item) => item);

      if (isSpotDifferent) {
        const description = compareDifferentDesc(
          originSpot.description,
          willCompareSpot.description,
        );

        const rate = compareDifferentRate(
          originSpot.rate,
          willCompareSpot.rate,
        );
        const image_url_list = compareDifferentImageUrl(
          originSpot.image_url_list,
          willCompareSpot.image_url_list,
        );
        return { id: originSpot.id, description, rate, image_url_list };
      } else return;
    }
  });

  return spotDifferences.filter((i) => i);
}

function isDifferentIdOrders(origin: number[], willCompare: number[]) {
  if (
    origin.length !== willCompare.length ||
    !origin.every((id, idx) => id === willCompare[idx])
  )
    return true;
}

function isDifferentTitle(origin: string, willCompare: string) {
  if (origin !== willCompare) return true;
}
function isDifferentDesc(origin: string, willCompare: string) {
  if (origin !== willCompare) return true;
}
function isDifferentRate(origin: number, willCompare: string) {
  if (origin.toFixed(1) !== willCompare) return true;
}
function isDifferentImageUrl(
  origin: string[],
  willCompare: { filename: string; uri: string }[],
) {
  if (
    origin.length !== willCompare.length ||
    willCompare.filter((item) => item.uri.startsWith('data:')).length
  )
    return true;
}

function compareDifferentTitle(origin: string, willCompare: string) {
  if (origin !== willCompare) return willCompare;
  else return origin;
}
function compareDifferentDesc(origin: string, willCompare: string) {
  if (origin !== willCompare) return willCompare;
  else return origin;
}
function compareDifferentRate(origin: number, willCompare: string) {
  if (origin.toFixed(1) !== willCompare) return willCompare;
  else return origin.toFixed(1);
}
function compareDifferentImageUrl(
  origin: string[],
  willCompare: { filename: string; uri: string }[],
) {
  if (
    origin.length !== willCompare.length ||
    willCompare.filter((item) => item.uri.startsWith('data:')).length
  )
    return willCompare;
  else
    return origin.map((image, idx) => ({
      filename: (idx + 1).toString(),
      uri: image,
    }));
}
