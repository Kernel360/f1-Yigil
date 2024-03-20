export default function Progress({ value }: { value: number }) {
  return (
    <div
      className="top-0 h-1 bg-black transition-all ease-in-out duration-300"
      style={{
        width: `${Math.floor((value / 3) * 100)}%`,
      }}
    ></div>
  );
}
