import { http, HttpResponse } from 'msw';

const handlers = [
  http.get(
    `http://localhost:8080/api/v1/places/static-image`,
    ({ request }) => {
      return HttpResponse.json({
        status: 200,
        data: { status: false },
      });
    },
  ),
];
export default handlers;
