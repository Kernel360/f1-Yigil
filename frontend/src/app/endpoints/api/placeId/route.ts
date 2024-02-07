// export const getArea = async (title: string): Promise<any[]> => {
//     const result = await fetch(
//       'https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?',
//       {
//         method: 'GET',
//         headers: {
//           'X-Naver-Client-Id': process.env.NAVER_SEARCH_ID,
//           'X-Naver-Client-Secret': process.env.NAVER_SEARCH_SECRET,
//         } as Record<string, string>,
//       },
//     );
//     const mapRes = await result.json();
//     return mapRes.items;
//   };
