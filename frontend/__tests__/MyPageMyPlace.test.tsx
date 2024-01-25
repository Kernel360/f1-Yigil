import MyPagePlaceItem from '@/app/_components/mypage/MyPagePlaceItem';
import MyPagePlaceList from '@/app/_components/mypage/MyPagePlaceList';
import { myPlaceData } from '@/mocks/api/data/myPlaceData';

import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

describe('MyPageMyPlace', () => {
  const placeDatas = myPlaceData.map((data) => [data]);
  it.each(placeDatas)('render each place item', async (data) => {
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
    const text = await screen.findAllByText(/여행/i);
    expect(text).toHaveLength(1);
  });
});
