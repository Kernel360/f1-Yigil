import { EventFor } from '@/types/type';
import IconWithCounts from '../IconWithCounts';
import StarIcon from '/public/icons/star.svg';
import Select from '../ui/select/Select';
import ImageHandler from '../images';
import { TImageData } from '../images/ImageHandler';

export const TextArea = ({
  description,
  onChangeHandler,
}: {
  description: string;
  onChangeHandler: (title: string) => void;
}) => {
  const onChange = (e: EventFor<'textarea', 'onChange'>) => {
    onChangeHandler(e.currentTarget.value);
  };
  return (
    <>
      <textarea
        placeholder={description}
        className="p-4 h-32 border-2 border-violet bg-gray-100 rounded-xl text-lg resize-none"
        onChange={onChange}
        value={description}
      />
      <span className="text-end text-gray-400 mt-[-16px] mr-4">
        {description.length} / 30
      </span>
    </>
  );
};

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
    <input
      className="text-2xl font-semibold border-2 border-violet rounded-md w-[180px] pl-1"
      type="text"
      placeholder={title}
      value={title}
      onChange={onChange}
    />
  );
};

const starList = Array.from({ length: 5 }, (v, idx) => ({
  label: (
    <span className="text-gray-500 text-xl leading-6">
      <IconWithCounts
        icon={<StarIcon className="w-6 h-6 fill-[#FAbb15] pb-[3px]" />}
        count={idx + 1}
        rating
      />
    </span>
  ),
  value: `${idx + 1}.0`,
}));

export const SelectContainer = ({
  selectOption,
  rate,
  onChangeSelectOption,
}: {
  selectOption: string;
  rate: string;
  onChangeSelectOption: (option: string | number) => void;
}) => {
  return (
    <span className="border-violet border-2 rounded-md">
      <Select
        list={starList}
        selectOption={selectOption}
        defaultValue={rate}
        onChangeSelectOption={onChangeSelectOption}
      />
    </span>
  );
};

export const ModifyImage = ({
  image_urls,
  setImages,
  order,
}: {
  image_urls: TImageData[];
  setImages: (newImages: TImageData[]) => void;
  order?: string;
}) => {
  return (
    <div className="h-1/3">
      <ImageHandler images={image_urls} setImages={setImages} order={order} />
    </div>
  );
};
