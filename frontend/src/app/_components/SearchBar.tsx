import SearchIcon from '/public/icons/search.svg';

export default function SearchBar() {
  return (
    <div className="w-4/5 mx-4 px-4 py-2 bg-[#e5e7eb] self-center shadow-xl rounded-full flex gap-4">
      <SearchIcon />
      <input
        className="text-lg grow border-0 bg-transparent"
        type="text"
        placeholder="검색어를 입력하세요."
      />
    </div>
  );
}
