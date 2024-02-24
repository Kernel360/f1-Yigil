import { sortOptions } from '@/app/_components/mypage/MyPageSelectBtns';
import { render, screen, fireEvent } from '@testing-library/react';
import Select from '@/app/_components/ui/select/Select';
import SelectOption from '@/app/_components/ui/select/SelectOption';

describe('Select Component', () => {
  const closeModal = vi.fn();
  const onChangeSortOption = vi.fn();

  it('render selectOption when isSortOpened is true', async () => {
    render(
      <Select
        list={sortOptions}
        selectOption="최신순"
        onChangeSelectOption={onChangeSortOption}
      />,
    );
    fireEvent.click(screen.getByLabelText('select'));
    const buttonElement = screen.getAllByRole('button');
    expect(buttonElement).toHaveLength(3);
  });

  it('call change sort option function on click', async () => {
    render(
      <>
        <Select
          list={sortOptions}
          selectOption="최신순"
          onChangeSelectOption={onChangeSortOption}
        />
        <SelectOption
          list={sortOptions}
          onChangeSelectOption={onChangeSortOption}
          closeModal={closeModal}
        />
      </>,
    );

    const rateButton = await screen.findByRole('button', { name: '별점순' });

    fireEvent.click(rateButton);

    expect(onChangeSortOption).toHaveBeenCalledWith('rate');
  });
});
