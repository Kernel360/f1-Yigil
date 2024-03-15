'use client';

import NaverContext from '@/context/NaverContext';
import StepProvider from '@/context/travel/step/StepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import SpotProvider from '@/context/travel/spot/SpotContext';
import AddTravelMapProvider from '@/context/map/AddTravelMapContext';
import SearchProvider from '@/context/search/SearchContext';
import AddSpotContent from './AddSpotContent';

export default function AddSpot({ ncpClientId }: { ncpClientId: string }) {
  return (
    <section className="h-full flex flex-col grow">
      <NaverContext ncpClientId={ncpClientId}>
        <PlaceProvider>
          <SpotProvider>
            <StepProvider>
              <AddTravelMapProvider>
                <SearchProvider>
                  <AddSpotContent />
                </SearchProvider>
              </AddTravelMapProvider>
            </StepProvider>
          </SpotProvider>
        </PlaceProvider>
      </NaverContext>
    </section>
  );
}
