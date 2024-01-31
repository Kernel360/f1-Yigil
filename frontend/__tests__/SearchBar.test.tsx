import '@testing-library/jest-dom';
import { render, screen } from '@testing-library/react';

import SearchBar from '@/app/_components/search/SearchBar';
import { describe, expect, it } from 'vitest';

vi.mock('next/navigation', () => {
  return {
    useSearchParams: () => {
      return { get: vi.fn() };
    },
    useRouter: () => {
      return { back: vi.fn(), replace: vi.fn() };
    },
    usePathname: vi.fn(),
  };
});

describe('SearchBar', () => {
  it('renders cancellable SearchBar', () => {
    //mocking prop
    const addResult = (result: string) => {};

    render(<SearchBar addResult={addResult} cancellable />);

    const button = screen.getByText('취소');

    expect(button).toBeInTheDocument();
  });
});
