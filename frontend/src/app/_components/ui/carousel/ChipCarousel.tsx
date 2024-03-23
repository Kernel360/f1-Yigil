'use client';

import useEmblaCarousel from 'embla-carousel-react';

import type { TSpotState } from '@/context/travel/schema';

import XIcon from '/public/icons/x.svg';

function bgColor(index: number) {
  switch (index) {
    case 0: {
      return 'bg-blue-400';
    }
    case 1: {
      return 'bg-blue-500';
    }
    case 2: {
      return 'bg-blue-600';
    }
    case 3: {
      return 'bg-blue-700';
    }
    case 4: {
      return 'bg-blue-800';
    }
  }

  return '';
}

export default function ChipCarousel({
  spots,
  onCancel,
}: {
  spots: TSpotState[];
  onCancel: (index: number) => void;
}) {
  const [emblaRef] = useEmblaCarousel({
    startIndex: spots.length - 1,
  });

  return (
    <div className="overflow-hidden" ref={emblaRef} aria-label="선택 장소 목록">
      <div className="p-2 flex gap-2">
        {spots.map(({ place }, index) => (
          <div
            className="max-w-52 px-3 py-2 border-2 border-blue-500 bg-white rounded-full flex gap-2 items-center shrink-0"
            key={`${place.name}-${index}`}
          >
            <span
              className={`w-8 h-8 shrink-0 rounded-full text-white ${bgColor(
                index,
              )} flex justify-center items-center text-lg font-medium select-none`}
            >
              {index + 1}
            </span>
            <span className="font-medium select-none truncate">
              {place.name}
            </span>
            <button
              className="p-[1px] bg-gray-300 rounded-full"
              onClick={() => onCancel(index)}
            >
              <XIcon className="w-5 h-5 stroke-2 stroke-white" />
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
