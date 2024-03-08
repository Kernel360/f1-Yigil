import BaseSearchBar from '@/app/_components/search/BaseSearchBar';

export default function SearchPage() {
  return (
    <main className="flex flex-col grow gap-4">
      <BaseSearchBar cancellable />
      <hr />
    </main>
  );
}
