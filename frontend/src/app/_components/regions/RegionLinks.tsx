import HorizontalDragScroll from './HorizontalDragScroll';
import RegionIcon from './RegionIcon';

import { regions } from './constants';

export default function RegionLinks() {
  return (
    <nav className="pt-4 flex flex-col gap-y-4" aria-label="regions">
      <span className="self-center">어디로 떠나볼까요?</span>
      <HorizontalDragScroll>
        {regions.map(({ slug, label }) => (
          <RegionIcon key={slug} slug={slug} label={label} />
        ))}
      </HorizontalDragScroll>
    </nav>
  );
}
