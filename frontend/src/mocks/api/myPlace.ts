import { http, HttpResponse } from 'msw';
import { myPlaceSpotData } from './data/myPlaceData';

const handlers = [
  http.get(`/api/v1/members/spot`, ({ request }) => {
    const [page, size, sortOrder, sortBy, selected] = request.url
      .split('?')[1]
      .split('&');

    console.log(request.url);

    const pageNum = Number(page.split('=')[1]);
    const sortOrdered = sortOrder.split('=')[1];
    const select = selected.split('=')[1];
    const sortedBy = sortBy.split('=')[1];

    const descPlaceData = myPlaceSpotData.sort((a, b) => a.postId - b.postId);
    const ascPlaceData = myPlaceSpotData.sort((a, b) => b.postId - a.postId);
    const ratePlaceData = myPlaceSpotData.sort((a, b) => b.rating - a.rating);

    if (select === 'all') {
      if (sortOrdered === 'desc') {
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: descPlaceData.filter((item, idx) => idx < 5),
            totalCount: 12,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: descPlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            totalCount: 12,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: descPlaceData.filter((item, idx) => idx > 10),
            totalCount: 12,
          });
        }
      } else if (sortOrdered === 'asc') {
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: ascPlaceData.filter((item, idx) => idx < 5),
            totalCount: 12,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: ascPlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            totalCount: 12,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: ascPlaceData.filter((item, idx) => idx > 10),
            totalCount: 12,
          });
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: ratePlaceData.filter((item, idx) => idx < 5),
            totalCount: 12,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: ratePlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            totalCount: 12,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: ratePlaceData.filter((item, idx) => idx > 10),
            totalCount: 12,
          });
        }
      }
    } else if (select === 'public') {
      if (sortOrdered === 'desc') {
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: descPlaceData
              .filter((item) => !item.isSecret)
              .filter((item, idx) => idx < 5),
            totalCount: 12,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: descPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: descPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        }
      } else if (sortOrder === 'asc') {
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: ascPlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: ascPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: ascPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1)
          return HttpResponse.json({
            status: 200,
            data: ratePlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        else if (pageNum === 2) {
          return HttpResponse.json({
            status: 200,
            data: ratePlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        } else if (pageNum === 3) {
          return HttpResponse.json({
            status: 200,
            data: ratePlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            totalCount: 12,
          });
        }
      }
    }
  }),

  http.get(`http://localhost:8080/api/v1/member/course`, () => {
    return HttpResponse.json({
      status: 200,
      data: { id: 1 },
      totalCount: 12,
    });
  }),
];
export default handlers;
