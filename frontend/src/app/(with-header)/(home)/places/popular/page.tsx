import { authenticateUser } from '@/app/_components/mypage/hooks/myPageActions';
import { myInfoSchema } from '@/types/response';
import { getMorePopularPlaces } from '../../action';
import { Place } from '@/app/_components/place';

export default async function PopularPlacesPage() {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const result = await getMorePopularPlaces();

  if (!result.success) {
    console.log(result);

    return <main>Failed</main>;
  }

  const places = result.data.places;

  return (
    <main className="px-4 max-w-full flex flex-col">
      <h1 className="pl-4 pt-4 text-3xl font-medium">인기 장소</h1>
      <div className="flex flex-col gap-4 relative">
        {places.map((place, index) => (
          <Place
            key={place.id}
            data={place}
            order={index}
            isLoggedIn={memberInfo.success}
          />
        ))}
      </div>
    </main>
  );
}
