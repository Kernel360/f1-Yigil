import eventBannerHandler from './api/eventBanner';
import myPlaceHandler from './api/myPlace';
import myPageHandler from './api/myPage';
const handlers = [...eventBannerHandler, ...myPlaceHandler, ...myPageHandler];
export default handlers;
