'use client';

export default function SetMethodButton({
  title,
  description,
  selected,
  onSelect,
}: {
  title: string;
  description: string;
  selected: boolean;
  onSelect: () => void;
}) {
  return (
    <button
      className={`w-full h-1/3 border rounded-xl shadow-lg ${
        selected && 'border-blue-500 bg-gray-100'
      } hover:border-blue-500 hover:bg-gray-100 transition-colors flex flex-col justify-center`}
      onClick={onSelect}
    >
      <div className="p-8 flex flex-col items-start">
        <span className="pb-4 text-3xl font-semibold">{title}</span>
        {description.split('\\n').map((line) => (
          <span
            key={line}
            className="text-xl text-start break-keep text-gray-500 font-light"
          >
            {line}
          </span>
        ))}
      </div>
    </button>
  );
}
