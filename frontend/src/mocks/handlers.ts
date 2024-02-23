import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
import placeHandler from './api/place';
import myPageHandler from './api/myPage';
import placeStaticImageHandler from './api/placeStaticImage';

const handlers = [
  ...eventBannerHandler,
  ...myPlaceHandler,
  ...placeHandler,
  ...myPageHandler,
  ...placeStaticImageHandler,
];
export default handlers;
