import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
import placeDetailHandler from './api/placeDetail';

const handlers = [
  ...eventBannerHandler,
  ...myPlaceHandler,
  ...placeDetailHandler,
];
export default handlers;
