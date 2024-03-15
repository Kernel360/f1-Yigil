import Link from 'next/link';
import Header from './_components/header/Header';
import XIcon from '/public/icons/x.svg';

export default function NotFound() {
  return (
    <>
      <Header />
      <div className="mx-9 h-full">
        <div className="h-1/4 flex items-end gap-x-3">
          <div className="text-[48px] leading-[60px] font-bold">
            <p>404</p>
            <p>Error</p>
          </div>
          <div className="border-[8px] border-red-500 rounded-full w-fit">
            <XIcon className="w-[100px] h-[100px] stroke-red-500 stroke-[4px] " />
          </div>
        </div>

        <div className="text-2xl leading-9 h-1/4 flex flex-col justify-center">
          <div>페이지를 찾을 수 없습니다.</div>
          <br />
          <p className="text-base break-keep">
            페이지가 존재하지 않거나, 사용할 수 없는 페이지입니다. 입력하신
            주소가 정확한지 다시 한번 확인해주세요.
          </p>
        </div>
        <ul className="flex flex-col items-center gap-y-4 ">
          <Link
            href={``}
            className="w-full py-4 flex justify-center items-center bg-gray-100 text-gray-500 text-2xl leading-7 font-semibold rounded-xl hover:bg-gray-200 hover:text-gray-400 active:bg-gray-300 active:text-white"
          >
            마이페이지로 바로가기
          </Link>
          <Link
            href="/"
            className="w-full py-4 flex justify-center items-center bg-gray-100 text-gray-500 text-2xl leading-7 font-semibold rounded-xl hover:bg-gray-200 hover:text-gray-400 active:bg-gray-300 active:text-white"
          >
            홈으로 바로가기
          </Link>
        </ul>
      </div>
    </>
  );
}
