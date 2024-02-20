import { http, HttpResponse } from 'msw';
import { eventBannerImgs } from './data/eventBannerImgs';

const handlers = [
  http.get(`http://localhost:8080/api/event`, () => {
    return HttpResponse.json({
      status: 200,
      data: eventBannerImgs,
    });
  }),
];
export default handlers;
