// import MyPageSpotItem from '@/app/_components/mypage/MyPageSpotItem';
// import { myPlaceData } from '@/mocks/api/data/myPlaceData';

// import '@testing-library/jest-dom';
// import { render, screen } from '@testing-library/react';

// describe('MyPageMySpot', () => {
//   const placeDatas = myPlaceData.map((data) => [data]);
//   it.each(placeDatas)('render each spot item', async (data) => {
//     const {
//       image_url,
//       post_id,
//       travel_id,
//       title,
//       description,
//       isSecret,
//       post_date,
//     } = data;
//     render(
//       <MyPageSpotItem
//         image_url={image_url}
//         title={title}
//         post_id={post_id}
//         travel_id={travel_id}
//         description={description}
//         isSecret={isSecret}
//         post_date={post_date}
//       />,
//     );
//     const text = await screen.findAllByText(/여행/i);
//     expect(text).toHaveLength(1);
//   });
// });
