import { useMemo } from 'react';
import LeftIcon from '/public/icons/chevron-left.svg';
import RightIcon from '/public/icons/chevron-right.svg';

interface PropsType {
  currentPage: number;
  setCurrentPage: (currentPage: number) => void;
  totalPage: number;
}

function Pagination({ currentPage, setCurrentPage, totalPage }: PropsType) {
  const renderPage = useMemo(() => {
    const start = Math.floor((currentPage - 1) / 5) * 5 + 1;
    const end = Math.min(start + 5 - 1, totalPage);
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }, [currentPage, totalPage, 5]);

  const addPage = () => {
    if (currentPage >= totalPage) return;
    setCurrentPage(currentPage + 1);
  };

  const minusPage = () => {
    if (currentPage <= 1) return;
    setCurrentPage(currentPage - 1);
  };
  const clickPage = (page: number) => {
    setCurrentPage(page);
  };
  return (
    <nav className={`my-4 pb-[50px] flex justify-center items-center gap-x-6`}>
      {currentPage !== 1 && (
        <button
          className="w-[9px] h-[16px] cursor-pointer px-1"
          onClick={minusPage}
          tabIndex={currentPage !== 1 ? 0 : -1}
        >
          <LeftIcon className="w-[9px] h-[16px] stroke-gray-300 hover:stroke-gray-500" />
        </button>
      )}

      {renderPage &&
        renderPage.map((v, idx) => (
          <button
            key={idx}
            className={`${
              currentPage === v
                ? 'font-semibold text-2xl'
                : 'font-normal text-gray-300 text-2xl'
            } cursor-pointer p-2`}
            onClick={() => clickPage(v)}
          >
            {v}
          </button>
        ))}
      {!renderPage.includes(totalPage) && currentPage !== totalPage && (
        <button
          className="w-[9px] h-[16px] cursor-pointer px-1"
          onClick={addPage}
          tabIndex={
            renderPage.includes(totalPage) && currentPage !== totalPage ? -1 : 0
          }
        >
          <RightIcon className="w-[9px] h-[16px] stroke-gray-300 hover:stroke-gray-500" />
        </button>
      )}
    </nav>
  );
}

export default Pagination;
