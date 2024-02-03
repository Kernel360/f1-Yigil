import '@testing-library/jest-dom';
import { fireEvent, render, screen } from '@testing-library/react';
import { headerPopOverData } from '@/app/_components/header/constants';

import PopOverIcon from '@/app/_components/ui/popover/PopOverItem';

describe('PopOver', () => {
  const datas = headerPopOverData.map((data) => [data]);

  const closeModal = () => {
    false;
  };

  it.each(datas)('render popover links', (data) => {
    const { href, label, onClick, Icon } = data;
    render(
      <PopOverIcon
        href={href}
        label={label}
        onClick={onClick}
        Icon={Icon}
        closeModal={closeModal}
      />,
    );

    const link = screen.getByRole('link');

    expect(link).toBeInTheDocument();
  });
});
