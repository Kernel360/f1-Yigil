import { http, HttpResponse } from 'msw';
import { myPageData } from './data/myPageData';

const handlers = [
  http.get('api/v1/members', ({ request }) => {
    if (!request.headers.get('cookie')?.slice(0).split('=')[1]) {
      return HttpResponse.json({
        status: 200,
        data: myPageData,
      });
    } else {
      return HttpResponse.json({
        status: 400,
        code: 9999,
      });
    }
  }),
];
export default handlers;
