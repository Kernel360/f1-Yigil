import MyPagePlaceItem from '@/app/_components/mypage/MyPagePlaceItem';
import MyPagePlaceList from '@/app/_components/mypage/MyPagePlaceList';
import { myPlaceData } from '@/mocks/api/data/myPlaceData';

import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

test('MyPageMyPlace', () => {
  const placeDatas = myPlaceData.map((data) => [data]);
  // render(<MyPagePlaceList placeList={myPlaceData} />);
  it.each(placeDatas)('render each place item', (data) => {
    const {
      imageUrl,
      post_id,
      travel_id,
      title,
      description,
      isSecret,
      post_date,
    } = data;
    render(
      <MyPagePlaceItem
        imageUrl={imageUrl}
        title={title}
        post_id={post_id}
        travel_id={travel_id}
        description={description}
        isSecret={isSecret}
        post_date={post_date}
      />,
    );
  });
});
