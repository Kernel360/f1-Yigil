import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';
import Carousel from '@/app/_components/carousel/Carousel';

test('이미지를 불러와서 렌더링하는지 확인', async () => {
  render(<Carousel />);

  const imgs = await screen.findAllByAltText('event-image');
  expect(imgs).toHaveLength(3);
});
