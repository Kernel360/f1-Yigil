import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

import PopOverIcon from '@/app/_components/ui/popover/PopOverItem';
import { headerPopOverData } from '@/app/_components/ui/popover/constants';

vi.mock('next/navigation', () => {
  return {
    useRouter: () => {
      return { back: vi.fn() };
    },
  };
});

describe('PopOver', () => {
  const closeModal = () => {
    false;
  };

  it.each(headerPopOverData)('render popover label', (data) => {
    render(<PopOverIcon data={data} closeModal={closeModal} />);

    const text = screen.queryByText(data.label);

    expect(text).toBeInTheDocument();
  });
});
