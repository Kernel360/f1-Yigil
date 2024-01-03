import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

import NavigationIcon from '@/app/_components/navigation/NavigationIcon';
import { navigationData } from '@/app/_components/navigation/constants';

describe('NavigationIcon', () => {
  const testCases = navigationData.map((data) => [data]);

  it.each(testCases)('renders Link component', (data) => {
    const { href, label, icon } = data;

    render(<NavigationIcon href={href} Icon={icon} label={label} />);

    const link = screen.getByRole('link');

    expect(link).toBeInTheDocument();
  });
});
