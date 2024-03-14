'use client';

import NaverContext from '@/context/NaverContext';
import StepProvider from '@/context/travel/step/StepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import SpotProvider from '@/context/travel/spot/SpotContext';
import AddTravelMapProvider from '../AddTravelMapProvider';

import AddSpotContent from './AddSpotContent';
import SearchProvider from '@/context/search/SearchContext';

export default function AddSpot({ ncpClientId }: { ncpClientId: string }) {
  return (
    <section className="h-full flex flex-col grow">
      <NaverContext ncpClientId={ncpClientId}>
        <StepProvider>
          <PlaceProvider>
            <SpotProvider>
              <AddTravelMapProvider>
                <SearchProvider>
                  <AddSpotContent />
                </SearchProvider>
              </AddTravelMapProvider>
            </SpotProvider>
          </PlaceProvider>
        </StepProvider>
      </NaverContext>
    </section>
  );
}
