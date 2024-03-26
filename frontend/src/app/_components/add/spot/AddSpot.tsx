'use client';

import NaverContext from '@/context/NaverContext';
import SpotStepProvider from '@/context/travel/step/spot/SpotStepContext';
import SpotProvider from '@/context/travel/spot/SpotContext';
import AddTravelMapProvider from '@/context/map/AddTravelMapContext';
import SearchProvider from '@/context/search/SearchContext';
import AddSpotContent from './AddSpotContent';

import type { TChoosePlace } from '@/context/travel/schema';

export default function AddSpot({
  ncpClientId,
  initialKeyword,
  initialPlace,
}: {
  ncpClientId: string;
  initialKeyword: string;
  initialPlace?: TChoosePlace;
}) {
  return (
    <section className="h-full flex flex-col grow">
      <NaverContext ncpClientId={ncpClientId}>
        <SpotProvider initialPlace={initialPlace}>
          <SpotStepProvider>
            <AddTravelMapProvider>
              <SearchProvider initialKeyword={initialKeyword}>
                <AddSpotContent />
              </SearchProvider>
            </AddTravelMapProvider>
          </SpotStepProvider>
        </SpotProvider>
      </NaverContext>
    </section>
  );
}
