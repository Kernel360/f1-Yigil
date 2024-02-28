import { http, HttpResponse } from 'msw';
import { bookmarkData } from './data/bookmarkData';

const handlers = [
  http.get('api/v1/bookmarks', ({ request }) => {
    const data = JSON.stringify({ content: bookmarkData, totalPage: 1 });
    return HttpResponse.json(
      {
        content: bookmarkData,
        totalPage: 1,
      },
      { headers: { 'Content-Length': Buffer.byteLength(data).toString() } },
    );
  }),
];

export default handlers;
