import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
import placeHandler from './api/place';
import myPageHandler from './api/myPage';

const handlers = [
  ...eventBannerHandler,
  ...myPlaceHandler,
  ...placeHandler,
  ...myPageHandler,
];
export default handlers;
