import { http, HttpResponse } from 'msw';
import { headers } from 'next/headers';
import { myPageData } from './data/myPageData';

const handlers = [
  http.get('http://localhost:8080/api/v1/members', ({ request }) => {
    return HttpResponse.json({
      status: 200,
      data: myPageData,
    });
  }),
];
export default handlers;
