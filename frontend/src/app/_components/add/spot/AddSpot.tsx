'use client';

import StepProvider from '@/context/travel/step/StepContext';
import SearchProvider from '@/context/search/SearchContext';

import BaseSearchBar from '../../search/BaseSearchBar';
import Navigation from './Navigation';
import Progress from './Progress';

export default function AddSpot() {
  return (
    <section className="flex flex-col grow">
      <StepProvider>
        <Progress />
        <Navigation />
        <SearchProvider>
          <BaseSearchBar />
        </SearchProvider>
      </StepProvider>
    </section>
  );
}
