import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
import placeHandler from './api/place';
import placeStaticImageHandler from './api/placeStaticImage';

const handlers = [
  ...eventBannerHandler,
  ...myPlaceHandler,
  ...placeHandler,
  ...placeStaticImageHandler,
];
export default handlers;
