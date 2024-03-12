'use client';

import StepProvider from '@/context/travel/step/StepContext';
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
        <SpotProvider>
          <AddSpotContent />
        </SpotProvider>
      </StepProvider>
    </section>
  );
}
