import { http, HttpResponse } from 'msw';
import { myPlaceCourseData, myPlaceSpotData } from './data/myPlaceData';

const handlers = [
  http.get(`http://localhost:8080/api/v1/member/spot`, ({ request }) => {
    return HttpResponse.json({
      status: 200,
      data: myPlaceSpotData,
    });
  }),
  http.get(`http://localhost:8080/api/v1/member/course`, () => {
    return HttpResponse.json({
      status: 200,
      data: myPlaceCourseData,
    });
  }),
];
export default handlers;
