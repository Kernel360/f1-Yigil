'use client';

import StepProvider from '@/context/travel/step/StepContext';
import PlaceProvider from '@/context/travel/place/PlaceContext';
import SpotProvider from '@/context/travel/spot/SpotContext';

import Navigation from '../Navigation';
import Progress from '../Progress';
import AddSpotContent from './AddSpotContent';

export default function AddSpot() {
  return (
    <section className="flex flex-col grow">
      <StepProvider>
        <Progress />
        <Navigation />
        <PlaceProvider>
          <SpotProvider>
            <AddSpotContent />
          </SpotProvider>
        </PlaceProvider>
      </StepProvider>
    </section>
  );
}
