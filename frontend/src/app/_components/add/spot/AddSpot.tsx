'use client';

import NaverContext from '@/context/NaverContext';
import SpotStepProvider from '@/context/travel/step/spot/SpotStepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import SpotProvider from '@/context/travel/spot/SpotContext';
import AddTravelMapProvider from '@/context/map/AddTravelMapContext';
import SearchProvider from '@/context/search/SearchContext';
import AddSpotContent from './AddSpotContent';

export default function AddSpot({
  ncpClientId,
  initialKeyword,
}: {
  ncpClientId: string;
  initialKeyword: string;
}) {
  return (
    <section className="h-full flex flex-col grow">
      <NaverContext ncpClientId={ncpClientId}>
        <PlaceProvider>
          <SpotProvider>
            <SpotStepProvider>
              <AddTravelMapProvider>
                <SearchProvider initialKeyword={initialKeyword}>
                  <AddSpotContent />
                </SearchProvider>
              </AddTravelMapProvider>
            </SpotStepProvider>
          </SpotProvider>
        </PlaceProvider>
      </NaverContext>
    </section>
  );
}
