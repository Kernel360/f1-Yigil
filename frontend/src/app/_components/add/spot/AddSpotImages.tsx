'use client';

import { InfoTitle } from '../common';
import SpotImageHandler from '../../images/SpotImageHandler';

export default function AddSpotImages() {
  return (
    <section className="flex flex-col justify-center grow">
      <InfoTitle label="사진" additionalLabel="을 업로드하세요." />
      <SpotImageHandler />
    </section>
  );
}
