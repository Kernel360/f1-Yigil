import { http, HttpResponse } from 'msw';
import { myPlaceCourseData, myPlaceSpotData } from './data/myPlaceData';

const handlers = [
  http.get(`api/v1/spots/my`, ({ request }) => {
    const [page, size, sortBy, sortOrder] = request.url
      .split('?')[1]
      .split('&');

    const selectedIdx = request.url.indexOf('selected');

    const selected =
      selectedIdx > -1 && request.url.slice(selectedIdx, selectedIdx + 16);
    const pageNum = Number(page.split('=')[1]);
    const sortOrdered = sortOrder.split('=')[1];
    const sortedBy = sortBy && sortBy.split('=')[1];
    const select = selected && selected.split('=')[1];

    if (select === 'all') {
      if (sortOrdered === 'desc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id < 6)
              .sort((a, b) => a.spot_id - b.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id < 6)
                .sort((a, b) => a.spot_id - b.spot_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
              .sort((a, b) => a.spot_id - b.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
                .sort((a, b) => a.spot_id - b.spot_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 11)
              .sort((a, b) => a.spot_id - b.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 11)
                .sort((a, b) => a.spot_id - b.spot_id),

              total_pages: 13,
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
            content: myPlaceSpotData
              .sort((a, b) => b.spot_id - a.spot_id)
              .filter((item, idx) => idx < 5),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .sort((a, b) => b.spot_id - a.spot_id)
                .filter((item, idx) => idx < 5),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .sort((a, b) => b.spot_id - a.spot_id)
              .filter((item, idx) => idx > 5 && idx <= 10),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .sort((a, b) => b.spot_id - a.spot_id)
                .filter((item, idx) => idx > 5 && idx <= 10),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .sort((a, b) => b.spot_id - a.spot_id)
              .filter((item, idx) => idx > 10),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .sort((a, b) => b.spot_id - a.spot_id)
                .filter((item, idx) => idx > 10),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .sort((a, b) => b.rate - a.rate)
              .filter((item, idx) => idx < 5),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .sort((a, b) => b.rate - a.rate)
                .filter((item, idx) => idx < 5),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .sort((a, b) => b.rate - a.rate)
              .filter((item, idx) => idx > 5 && idx <= 10),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .sort((a, b) => b.rate - a.rate)
                .filter((item, idx) => idx > 5 && idx <= 10),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item, idx) => idx > 11)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .sort((a, b) => b.rate - a.rate)
                .filter((item, idx) => idx > 11),
              total_pages: 13,
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
            content: myPlaceSpotData
              .sort((a, b) => a.spot_id - b.spot_id)
              .filter((item, idx) => idx < 6)
              .filter((item) => !item.is_private),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .sort((a, b) => a.spot_id - b.spot_id)
                .filter((item, idx) => idx < 6)
                .filter((item) => !item.is_private),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.is_private)
              .sort((a, b) => a.spot_id - b.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => !item.is_private)
                .sort((a, b) => a.spot_id - b.spot_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item, idx) => idx > 10)
              .filter((item) => !item.is_private)
              .sort((a, b) => a.spot_id - b.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item, idx) => idx > 10)
                .filter((item) => !item.is_private)
                .sort((a, b) => a.spot_id - b.spot_id),
              total_pages: 13,
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
            content: myPlaceSpotData
              .filter((item) => item.spot_id < 6)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id < 6)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 11)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 11)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id < 6)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id < 6)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 11)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 11)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
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
            content: myPlaceSpotData
              .filter((item) => item.spot_id < 6)
              .filter((item) => item.is_private)
              .sort((a, b) => a.spot_id - b.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id < 6)
                .filter((item) => item.is_private)
                .sort((a, b) => a.spot_id - b.spot_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item, idx) => idx > 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item, idx) => idx > 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),
              total_pages: 13,
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
            content: myPlaceSpotData
              .filter((item, idx) => idx < 6)
              .filter((item) => item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item, idx) => idx < 6)
                .filter((item) => item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item, idx) => idx > 6 && idx <= 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item, idx) => idx > 6 && idx <= 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item, idx) => idx > 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.spot_id - a.spot_id),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item, idx) => idx > 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.spot_id - a.spot_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id < 6)
              .filter((item) => item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id < 6)
                .filter((item) => item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 6 && item.spot_id <= 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceSpotData
              .filter((item) => item.spot_id > 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceSpotData
                .filter((item) => item.spot_id > 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
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

  http.get(`api/v1/courses/my`, ({ request }) => {
    const [page, size, sortBy, sortOrder] = request.url
      .split('?')[1]
      .split('&');

    const selectedIdx = request.url.indexOf('selected');

    const selected =
      selectedIdx > -1 && request.url.slice(selectedIdx, selectedIdx + 16);
    const pageNum = Number(page.split('=')[1]);
    const sortOrdered = sortOrder.split('=')[1];
    const sortedBy = sortBy && sortBy.split('=')[1];
    const select = selected && selected.split('=')[1];

    if (select === 'all') {
      if (sortOrdered === 'desc') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id < 6)
              .sort((a, b) => a.course_id - b.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id < 6)
                .sort((a, b) => a.course_id - b.course_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 5 && item.course_id <= 11)
              .sort((a, b) => a.course_id - b.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 5 && item.course_id <= 11)
                .sort((a, b) => a.course_id - b.course_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 11)
              .sort((a, b) => a.course_id - b.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 11)
                .sort((a, b) => a.course_id - b.course_id),

              total_pages: 13,
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
            content: myPlaceCourseData
              .filter((item) => item.course_id < 6)
              .sort((a, b) => b.course_id - a.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id < 6)
                .sort((a, b) => b.course_id - a.course_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 6 && item.course_id <= 11)
              .sort((a, b) => b.course_id - a.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 6 && item.course_id <= 11)
                .sort((a, b) => b.course_id - a.course_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 11)
              .sort((a, b) => b.course_id - a.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 11)
                .sort((a, b) => b.course_id - a.course_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .sort((a, b) => b.rate - a.rate)
              .filter((item, idx) => idx < 5),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item, idx) => idx < 5)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .sort((a, b) => b.rate - a.rate)
              .filter((item, idx) => idx > 5 && idx <= 10),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .sort((a, b) => b.rate - a.rate)
                .filter((item, idx) => idx > 5 && idx <= 10),
              total_pages: 13,
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
            content: myPlaceCourseData
              .sort((a, b) => a.course_id - b.course_id)
              .filter((item, idx) => idx < 6)
              .filter((item) => !item.is_private),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .sort((a, b) => a.course_id - b.course_id)
                .filter((item, idx) => idx < 6)
                .filter((item) => !item.is_private),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item, idx) => idx > 5 && idx <= 10)
              .filter((item) => !item.is_private)
              .sort((a, b) => a.course_id - b.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item, idx) => idx > 5 && idx <= 10)
                .filter((item) => !item.is_private)
                .sort((a, b) => a.course_id - b.course_id),
              total_pages: 13,
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
            content: myPlaceCourseData
              .filter((item) => item.course_id < 6)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.course_id - a.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id < 6)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.course_id - a.course_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 6 && item.course_id <= 11)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.course_id - a.course_id),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 6 && item.course_id <= 11)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.course_id - a.course_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 3) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 11)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.course_id - a.course_id),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 11)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.course_id - a.course_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id < 6)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id < 6)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 6 && item.course_id <= 11)
              .filter((item) => !item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 6 && item.course_id <= 11)
                .filter((item) => !item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
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
            content: myPlaceCourseData
              .filter((item) => item.course_id < 6)
              .filter((item) => item.is_private)
              .sort((a, b) => a.course_id - b.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id < 6)
                .filter((item) => item.is_private)
                .sort((a, b) => a.course_id - b.course_id),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 6 && item.course_id <= 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.course_id - a.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 6 && item.course_id <= 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.course_id - a.course_id),
              total_pages: 13,
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
            content: myPlaceCourseData
              .filter((item, idx) => idx < 6)
              .filter((item) => item.is_private)
              .sort((a, b) => b.course_id - a.course_id),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item, idx) => idx < 6)
                .filter((item) => item.is_private)
                .sort((a, b) => b.course_id - a.course_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item, idx) => idx > 6 && idx <= 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.course_id - a.course_id),

            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item, idx) => idx > 6 && idx <= 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.course_id - a.course_id),

              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        }
      } else if (sortedBy === 'rate') {
        if (pageNum === 1) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id < 6)
              .filter((item) => item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id < 6)
                .filter((item) => item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
            },
            {
              headers: {
                'Content-Length': Buffer.byteLength(data).toString(),
              },
            },
          );
        } else if (pageNum === 2) {
          const data = JSON.stringify({
            content: myPlaceCourseData
              .filter((item) => item.course_id > 6 && item.course_id <= 11)
              .filter((item) => item.is_private)
              .sort((a, b) => b.rate - a.rate),
            total_pages: 13,
          });
          return HttpResponse.json(
            {
              content: myPlaceCourseData
                .filter((item) => item.course_id > 6 && item.course_id <= 11)
                .filter((item) => item.is_private)
                .sort((a, b) => b.rate - a.rate),
              total_pages: 13,
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
];
export default handlers;
