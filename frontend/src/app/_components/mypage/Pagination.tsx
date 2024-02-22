import { useEffect, useState } from 'react';

interface PropsType {
  currentPage: number;
  setCurrentPage: (currentPage: number) => void;
  allCount: number;
  divide: number;
}

function Pagination({
  currentPage,
  setCurrentPage,
  allCount,
  divide,
}: PropsType) {
  const [pagesCount, setPagesCount] = useState<number[] | []>([]);

  const maxPage = Math.ceil(allCount / divide);
  useEffect(() => {
    if (maxPage) {
      const arr = [];
      for (let i = 1; i <= maxPage; i++) {
        arr.push(i);
      }
      setPagesCount(arr);
    }
  }, []);

  const addPage = () => {
    if (currentPage >= maxPage) return;
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
    <div className={`my-5 pb-10 flex justify-center items-center`}>
      <span className="hover:cursor-pointer px-4" onClick={minusPage}>
        <svg
          width="5"
          height="15"
          viewBox="0 0 7 17"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M5.99805 1L1.49919 8.5L5.99805 16"
            stroke="#6D6D6D"
            strokeWidth="0.975844"
          />
        </svg>
      </span>
      {pagesCount &&
        allCount &&
        pagesCount.map((page: number) => (
          <span
            key={page}
            className={`${
              currentPage === page
                ? 'font-bold text-main'
                : 'font-normal text-gray-500'
            }  cursor-pointer hover:font-bold hover:text-main mx-2`}
            onClick={() => clickPage(page)}
          >
            {page}
          </span>
        ))}
      <span className="hover:cursor-pointer mx-4" onClick={addPage}>
        <svg
          width="5"
          height="15"
          viewBox="0 0 7 17"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M0.998047 16L5.4969 8.5L0.998047 1"
            stroke="#6D6D6D"
            strokeWidth="0.975844"
          />
        </svg>
      </span>
    </div>
  );
}

export default Pagination;
