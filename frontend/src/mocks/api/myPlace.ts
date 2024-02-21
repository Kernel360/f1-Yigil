import { http, HttpResponse } from 'msw';
import { myPlaceCourseData, myPlaceSpotData } from './data/myPlaceData';

const handlers = [
  http.get(`/api/v1/members/spot`, ({ request }) => {
    const [page, size, sortOrder, selected] = request.url
      .split('?')[1]
      .split('&');

    const pageNum = Number(page.split('=')[1]);
    const sortOrdered = sortOrder.split('=')[1];
    const select = selected.split('=')[1];

    if (select === 'all') {
      if (sortOrdered === 'desc') {
        const sortedPlaceData = myPlaceSpotData.sort(
          (a, b) => a.postId - b.postId,
        );
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData.filter((item, idx) => idx < 5),
            pageSize: 10,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            pageSize: 10,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData.filter((item, idx) => idx > 10),
            pageSize: 10,
            last: true,
          });
        }
      } else {
        const sortedPlaceData = myPlaceSpotData.sort(
          (a, b) => b.postId - a.postId,
        );
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData.filter((item, idx) => idx < 5),
            pageSize: 10,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            pageSize: 10,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData.filter((item, idx) => idx > 10),
            pageSize: 10,
            last: true,
          });
        }
      }
    } else if (select === 'public') {
      if (sortOrdered === 'desc') {
        const sortedPlaceData = myPlaceSpotData.sort(
          (a, b) => a.postId - b.postId,
        );
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData
              .filter((item) => !item.isSecret)
              .filter((item, idx) => idx < 5),
            pageSize: 10,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            pageSize: 10,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            pageSize: 10,
            last: true,
          });
        }
      } else {
        const sortedPlaceData = myPlaceSpotData.sort(
          (a, b) => b.postId - a.postId,
        );
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => !item.isSecret),
            pageSize: 10,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            pageSize: 10,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: sortedPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            pageSize: 10,
            last: true,
          });
        }
      }
    }
  }),

  http.get(`http://localhost:8080/api/v1/member/course`, () => {
    return HttpResponse.json({
      status: 200,
      data: { id: 1 },
      pageSize: 10,
    });
  }),
];
export default handlers;
