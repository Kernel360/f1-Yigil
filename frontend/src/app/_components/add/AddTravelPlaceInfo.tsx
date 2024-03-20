import Image from 'next/image';

import PlaceholderImage from '/public/images/placeholder.png';

export default function AddTravelPlaceInfo({
  name,
  address,
  mapImageUrl,
}: {
  name: string;
  address: string;
  mapImageUrl: string;
}) {
  return (
    <section className="p-4 flex flex-col gap-8">
      <div className="px-4 flex justify-between items-center">
        <span className="pr-8 text-lg text-gray-400 shrink-0">이름</span>
        <span className="text-2xl font-medium text-end">
          {name.replace('&amp;', '&')}
        </span>
      </div>
      <div className="px-4 flex justify-between items-center">
        <span className="pr-8 text-lg text-gray-400 shrink-0">주소</span>
        <span className="text-xl font-medium text-end">
          {address.replace('&amp;', '&')}
        </span>
      </div>
      <div className="my-4 relative aspect-video">
        <Image
          className="rounded-xl object-cover"
          priority
          src={mapImageUrl}
          placeholder="blur"
          blurDataURL={PlaceholderImage.blurDataURL}
          alt={`${name} 지도 이미지`}
          fill
        />
      </div>
    </section>
  );
}
