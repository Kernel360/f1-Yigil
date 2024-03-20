import { EventFor } from '@/types/type';

export const TitleInput = ({
  title,
  onChangeTitle,
}: {
  title: string;
  onChangeTitle: (title: string) => void;
}) => {
  const onChange = (e: EventFor<'input', 'onChange'>) => {
    onChangeTitle(e.currentTarget.value);
  };
  return (
    <span className="w-fit text-2xl font-semibold border-2 border-violet rounded-md">
      <input
        className="w-fit"
        type="text"
        placeholder={title}
        value={title}
        onChange={onChange}
      />
    </span>
  );
};
