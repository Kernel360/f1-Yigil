import React, { ReactElement, useEffect } from 'react';

export default function SelectOption({
  list,
  optionStyle,
  onChangeSelectOption,
  closeModal,
}: {
  list: { value: string; label: string | ReactElement }[];
  optionStyle?: string;
  onChangeSelectOption: (option: string) => void;
  closeModal: () => void;
}) {
  const mouseEventPrevent = (e: Event) => {
    e.preventDefault();
  };
  const keyBoardArrowPrevent = (e: KeyboardEvent) => {
    if (e.key === 'ArrowDown' || e.key === 'ArrowUp') e.preventDefault();
  };
  useEffect(() => {
    window.addEventListener('DOMMouseScroll', mouseEventPrevent, false); // older FF
    window.addEventListener('wheel', mouseEventPrevent, { passive: false }); // modern desktop
    window.addEventListener('touchmove', mouseEventPrevent, { passive: false }); // mobile
    window.addEventListener('keydown', keyBoardArrowPrevent);
    return () => {
      window.removeEventListener('DOMMouseScroll', mouseEventPrevent, false);
      window.removeEventListener('wheel', mouseEventPrevent, false);
      window.removeEventListener('touchmove', mouseEventPrevent);
      window.removeEventListener('keydown', keyBoardArrowPrevent);
    };
  }, []);
  return (
    <>
      <div
        className={`fixed inset-0 max-w-[430px] mx-auto z-20 cursor-default`}
        onClick={closeModal}
      ></div>
      <div
        className={`flex flex-col w-full absolute ${
          optionStyle ? optionStyle : 'top-10 left-0 bg-white'
        } border py-1 rounded-md z-30`}
      >
        {list.map(({ label, value }) => (
          <button
            key={value}
            value={value}
            className="cursor-pointer text-gray-700 py-[5px] flex justify-center"
            onClick={(e) => onChangeSelectOption(e.currentTarget.value)}
            onKeyDown={(e) =>
              e.key === 'Enter' && onChangeSelectOption(e.currentTarget.value)
            }
          >
            {label}
          </button>
        ))}
      </div>
    </>
  );
}
