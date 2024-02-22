import { useContext, useState } from 'react';
import { useRouter } from 'next/navigation';

import { AddSpotContext } from '../../spot/SpotContext';

import XMarkIcon from '/public/icons/x-mark.svg';
import Dialog from '@/app/_components/ui/dialog/Dialog';

import type { EventFor } from '@/types/type';
import type { TStep } from './types';
import { dataUrlToBlob } from '@/utils';
import { postSpotData } from '../action';

/**
 * `next` - 상위 컴포넌트에서 `dispatch({ type: 'next' })`를 감싼 이벤트 핸들러
 *
 * `previous` - 상위 컴포넌트에서 `dispatch({ type: 'previous' })`를 감싼 이벤트 핸들러
 *
 * @todo 입력이 없을 때 넘어가지 못하게 하는 코드 작성
 *
 * @todo 스팟 등록 명세서 최신화되면 맞춰서 요청 날리는 코드 작성
 */
export default function StepNavigation({
  currentStep,
  next,
  previous,
}: {
  currentStep: TStep;
  next: () => void;
  previous: () => void;
}) {
  const { back } = useRouter();

  const [isOpen, setIsOpen] = useState(false);

  const { makingStep } = currentStep;
  const { label, value } = makingStep.data;

  const state = useContext(AddSpotContext);

  const onKeyDown = (e: EventFor<'button', 'onKeyDown'>) => {
    if (e.key === 'Enter') setIsOpen(true);
    else if (e.key === 'Escape') setIsOpen(false);
  };

  function closeModal() {
    setIsOpen(false);
  }

  async function handleConfirm() {
    const { name, address, spotMapImageUrl, images, rating, review } = state;

    const parsedImages = images.map(
      ({ filename, uri }) => new File([dataUrlToBlob(uri)], filename),
    );

    const data = new FormData();
    data.append('place_name', name);
    data.append('place_address', address);
    data.append('rate', rating.toString());
    data.append('description', review.review);

    parsedImages.forEach((image) => data.append('files', image));

    if (spotMapImageUrl.startsWith('data://')) {
      data.append(
        'map_static_image_file',
        new File([dataUrlToBlob(spotMapImageUrl)], `${name} 지도 이미지`),
      );
    }

    await postSpotData(data);

    setIsOpen(false);
    next();
  }

  if (label === '완료') {
    return (
      <nav className="mx-2 py-4 flex justify-center items-center relative">
        <span className="text-xl text-semibold text-gray-900">{label}</span>
      </nav>
    );
  }

  return (
    <nav className="mx-2 py-4 flex justify-between items-center relative">
      {value === 1 ? (
        <button className="w-12 p-2" onClick={back}>
          <XMarkIcon className="w-6 h-6 stroke-gray-500" />
        </button>
      ) : (
        <button className="w-12 p-2" onClick={previous}>
          이전
        </button>
      )}

      <span className="text-xl text-semibold text-gray-900">{label}</span>
      {label.includes('확정') ? (
        <button
          className="w-12 p-2 text-main"
          onClick={() => setIsOpen(!isOpen)}
          onKeyDown={onKeyDown}
        >
          확정
          {isOpen && (
            <Dialog
              text="장소를 확정하시겠나요?"
              handleConfirm={handleConfirm}
              closeModal={closeModal}
            />
          )}
        </button>
      ) : (
        <button className="w-12 p-2 text-gray-500" onClick={next}>
          다음
        </button>
      )}
    </nav>
  );
}
