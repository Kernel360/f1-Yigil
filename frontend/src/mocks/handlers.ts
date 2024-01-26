import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
const handlers = [...eventBannerHandler, ...myPlaceHandler];
export default handlers;
