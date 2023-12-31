import RegionIcon from './RegionIcon';
import { regions } from './constants';

export default function RegionLinks() {
  return (
    <nav className="pt-4 flex flex-col gap-y-4" aria-label="regions">
      <span className="self-center">어디로 떠나볼까요?</span>
      <div
        tabIndex={-1}
        className="flex justify-between whitespace-nowrap overflow-x-auto"
      >
        {regions.map(({ slug, label }) => (
          <RegionIcon key={slug} slug={slug} label={label} />
        ))}
      </div>
    </nav>
  );
}
