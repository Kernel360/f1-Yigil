import { http, HttpResponse } from 'msw';
import { myPlaceSpotData } from './data/myPlaceData';

const handlers = [
  http.get(`api/v1/members/spots`, ({ request }) => {
    const [page, size, sortOrder] = request.url.split('?')[1].split('&');
    const sortByIdx = request.url.indexOf('sortBy');

    const selectedIdx = request.url.indexOf('selected');
    const rate = sortByIdx > -1 && request.url.slice(sortByIdx, sortByIdx + 11);
    const selected =
      selectedIdx > -1 && request.url.slice(selectedIdx, selectedIdx + 16);
    const pageNum = Number(page.split('=')[1]);
    const sortOrdered = sortOrder.split('=')[1];
    const sortBy = rate && rate.split('=')[1];
    const select = selected && selected.split('=')[1];

    const descPlaceData = myPlaceSpotData.sort((a, b) => a.spot_id - b.spot_id);
    const ascPlaceData = myPlaceSpotData.sort((a, b) => b.spot_id - a.spot_id);
    const ratePlaceData = myPlaceSpotData.sort((a, b) => b.rating - a.rating);

    if (select === 'all') {
      if (sortOrdered === 'desc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: descPlaceData.filter((item, idx) => idx < 5),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData.filter((item, idx) => idx < 5),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: descPlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData.filter(
                (item, idx) => idx > 5 && idx <= 10,
              ),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: descPlaceData.filter((item, idx) => idx > 10),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData.filter((item, idx) => idx > 10),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortOrdered === 'asc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: ascPlaceData.filter((item, idx) => idx < 5),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData.filter((item, idx) => idx < 5),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: ascPlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData.filter((item, idx) => idx > 5 && idx <= 10),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: ascPlaceData.filter((item, idx) => idx > 10),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData.filter((item, idx) => idx > 10),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: ratePlaceData.filter((item, idx) => idx < 5),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData.filter((item, idx) => idx < 5),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: ratePlaceData.filter((item, idx) => idx > 5 && idx <= 10),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData.filter(
                (item, idx) => idx > 5 && idx <= 10,
              ),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: ratePlaceData.filter((item, idx) => idx > 10),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData.filter((item, idx) => idx > 10),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      }
    } else if (select === 'public') {
      if (sortOrdered === 'desc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: descPlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData
                .filter((item, idx) => idx < 5)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: descPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: descPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData
                .filter((item, idx) => idx > 10)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortOrder === 'asc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: ascPlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData
                .filter((item, idx) => idx < 5)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: ascPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: ascPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData
                .filter((item, idx) => idx > 10)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: ratePlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData
                .filter((item, idx) => idx < 5)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: ratePlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: ratePlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData
                .filter((item, idx) => idx > 10)
                .filter((item) => !item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      }
    } else {
      if (sortOrdered === 'desc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: descPlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData
                .filter((item, idx) => idx < 5)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: descPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: descPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: descPlaceData
                .filter((item, idx) => idx > 10)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortOrdered === 'asc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: ascPlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData
                .filter((item, idx) => idx < 5)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: ascPlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: ascPlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ascPlaceData
                .filter((item, idx) => idx > 10)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: ratePlaceData
              .filter((item, idx) => idx < 5)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData
                .filter((item, idx) => idx < 5)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: ratePlaceData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: ratePlaceData
              .filter((item, idx) => idx > 10)
              .filter((item) => item.isSecret),
            totalPage: 13,
          });
          return HttpResponse.json(
            {
              content: ratePlaceData
                .filter((item, idx) => idx > 10)
                .filter((item) => item.isSecret),
              totalPage: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      }
    }
  }),

  http.get(`http://localhost:8080/api/v1/member/course`, () => {
    return HttpResponse.json({
      status: 200,
      data: { id: 1 },
      totalPage: 13,
    });
  }),
];
export default handlers;
