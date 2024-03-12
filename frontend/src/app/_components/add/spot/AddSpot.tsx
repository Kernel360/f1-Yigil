'use client';

import StepProvider from '@/context/travel/step/StepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import SpotProvider from '@/context/travel/spot/SpotContext';

import AddSpotContent from './AddSpotContent';

export default function AddSpot() {
  return (
    <section className="h-full flex flex-col grow">
      <StepProvider>
        <PlaceProvider>
          <SpotProvider>
            <AddSpotContent />
          </SpotProvider>
        </PlaceProvider>
      </StepProvider>
    </section>
  );
}
