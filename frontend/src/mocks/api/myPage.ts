import { http, HttpResponse } from 'msw';
import { myPageData } from './data/myPageData';

const handlers = [
  http.get('api/v1/members', ({ request }) => {
    return HttpResponse.json({
      status: 200,
      data: myPageData,
    });
  }),
];
export default handlers;
