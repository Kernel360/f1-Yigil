import { http, HttpResponse } from 'msw';
import { myPageData } from './data/myPageData';

const handlers = [
  http.get('api/v1/members', ({ cookies }) => {
    if (cookies.SESSION) {
      return HttpResponse.json({
        status: 200,
        code: 0,
        ...myPageData,
      });
    } else {
      return HttpResponse.json({
        status: 400,
        code: 9201,
      });
    }
  }),
];
export default handlers;
