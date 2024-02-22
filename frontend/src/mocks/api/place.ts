import { http, HttpResponse } from 'msw';
import { placeDetailData, placesData } from './data/placeData';

const handlers = [
  http.get('http://localhost:8080/api/v1/places', () => {
    const data = JSON.stringify(placesData);

    return HttpResponse.json(placesData, {
      headers: {
        'Content-Length': Buffer.byteLength(data).toString(),
      },
    });
  }),

  http.get('http://localhost:8080/api/v1/places/fail', () => {
    const body = {
      code: 1001,
      message: '장소 목록을 불러올 수 없습니다.',
    };

    const data = JSON.stringify(body);

    return HttpResponse.json(body, {
      status: 400,
      headers: { 'Content-Length': Buffer.byteLength(data).toString() },
    });
  }),

  http.get(`http://localhost:8080/api/v1/places/1`, () => {
    const data = JSON.stringify(placeDetailData);

    return HttpResponse.json(placeDetailData, {
      headers: {
        'Content-Length': Buffer.byteLength(data).toString(),
      },
    });
  }),

  http.get('http://localhost:8080/api/v1/places/1/fail', () => {
    const body = {
      code: 1001,
      message: '장소를 찾을 수 없습니다.',
    };

    const data = JSON.stringify(body);

    return HttpResponse.json(data, {
      status: 400,
      headers: { 'Content-Length': Buffer.byteLength(data).toString() },
    });
  }),
];

export default handlers;
