import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
import placeHandler from './api/place';

const handlers = [...eventBannerHandler, ...myPlaceHandler, ...placeHandler];
export default handlers;
