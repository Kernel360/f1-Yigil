import Tab, { type Item } from './Tab';

export default function TabGroup({
  path,
  parallelRoutesKey,
  label,
  items,
}: {
  path: string;
  parallelRoutesKey?: string;
  label?: string;
  items: Item[];
}) {
  return (
    <nav className="px-4 flex gap-2">
      {items.map((item) => (
        <Tab
          key={`${path}${item.slug}`}
          item={item}
          path={path}
          label={label}
          parallelRoutesKey={parallelRoutesKey}
        />
      ))}
    </nav>
  );
}
