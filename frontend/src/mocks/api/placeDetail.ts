import { http, HttpResponse } from 'msw';
import { placeDetailData } from './data/placeDetailData';

const handlers = [
  http.get(`http://localhost:8080/api/v1/places/1`, ({ request }) => {
    const data = JSON.stringify(placeDetailData);

    return HttpResponse.json(placeDetailData, {
      headers: {
        'Content-Length': Buffer.byteLength(data).toString(),
      },
    });
  }),
];

export default handlers;
