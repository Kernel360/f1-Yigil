import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
import placeHandler from './api/place';
import myPageHandler from './api/myPage';
import placeStaticImageHandler from './api/placeStaticImage';
import bookmarkHandler from './api/bookmark';
const handlers = [
  ...eventBannerHandler,
  ...myPlaceHandler,
  ...placeHandler,
  ...myPageHandler,
  ...placeStaticImageHandler,
  ...bookmarkHandler,
];
export default handlers;
