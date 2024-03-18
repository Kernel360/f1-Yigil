'use client';
import React, { useState } from 'react';
import BackButton from '../../place/BackButton';
import IconWithCounts from '../../IconWithCounts';
import StarIcon from '/public/icons/star.svg';
import { TMyPageSpotDetail } from '@/types/myPageResponse';
import Select from '../../ui/select/Select';
import useEmblaCarousel from 'embla-carousel-react';
import Image from 'next/image';
import ImageHandler from '../../images';
import { TImageData } from '../../images/ImageHandler';
import { patchMyPageSpotDetail } from '../hooks/myPageActions';
import Dialog from '../../ui/dialog/Dialog';
import ToastMsg from '../../ui/toast/ToastMsg';

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

export interface TModifyDetail {
  id: number;
  rate: string;
  description: string;
  image_urls: { filename: string; uri: string }[];
}

export default function SpotDetail({
  spotDetail,
  spotId,
}: {
  spotDetail: TMyPageSpotDetail;
  spotId: number;
}) {
  const {
    rate,
    place_name,
    place_address,
    map_static_image_file_url,
    image_urls,
    create_date,
    description,
  } = spotDetail;
  const [isModifyMode, setIsModifyMode] = useState(false);
  const [selectOption, setSelectOption] = useState(Number(rate).toFixed(1));

  const [modifyDetail, setModifyDetail] = useState<TModifyDetail>({
    id: spotId,
    rate,
    description,
    image_urls: image_urls.map((uri, idx) => ({
      filename: idx.toString(),
      uri,
    })),
  });

  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const [errorText, setErrorText] = useState('');

  const [emblaRef] = useEmblaCarousel({
    loop: false,
    dragFree: true,
  });

  const setImages = (newImages: TImageData[]) => {
    setModifyDetail({ ...modifyDetail, image_urls: newImages });
  };

  const onChangeSelectOption = (option: string | number) => {
    if (typeof option === 'number') return;
    setSelectOption(option);
  };

  const onClickComplete = () => {
    try {
      patchMyPageSpotDetail(spotId, modifyDetail);
    } catch (error) {
      setErrorText('수정에 실패했습니다.');
    } finally {
      setIsDialogOpened(false);
      setIsModifyMode(false);
    }
  };

  const closeModal = () => {
    setIsDialogOpened(false);
  };
  return (
    <div className="mx-4">
      <nav className="relative py-4 flex justify-between items-center">
        <BackButton />
        <span className="text-2xl font-light">
          {isModifyMode ? '기록 수정' : '장소 기록'}
        </span>

        <button
          className={`${isModifyMode ? 'text-main' : 'text-gray-500'} py-2`}
          onClick={() => setIsModifyMode(true)}
        >
          {isModifyMode ? (
            <span onClick={() => setIsDialogOpened(true)}>완료</span>
          ) : (
            '수정'
          )}
        </button>
      </nav>
      <section className="h-full p-2 flex flex-col gap-5 grow justify-between">
        <div className="flex justify-between items-center">
          <span className="text-2xl font-semibold">{place_name}</span>
          {isModifyMode ? (
            <span className="border-violet border-2 rounded-md">
              <Select
                list={starList}
                selectOption={selectOption}
                defaultValue={rate}
                onChangeSelectOption={onChangeSelectOption}
              />
            </span>
          ) : (
            <span className="text-gray-500 text-xl leading-6 flex items-center p-2 border-2 border-transparent">
              <IconWithCounts
                icon={<StarIcon className="w-6 h-6 fill-[#FAbb15]" />}
                count={parseInt(rate)}
                rating
              />
            </span>
          )}
        </div>
        <p className="text-gray-500">{place_address}</p>
        <div className="h-1/3 relative aspect-video">
          <Image
            className="rounded-md"
            src={map_static_image_file_url}
            alt="Spot map image"
            fill
          />
        </div>

        {isModifyMode ? (
          <div className="h-1/3">
            <ImageHandler
              images={modifyDetail.image_urls}
              setImages={setImages}
            />
          </div>
        ) : (
          <div className="overflow-hidden" ref={emblaRef}>
            <div className="flex gap-2">
              {image_urls.map((image) => (
                <div
                  className="p-2 relative w-1/3 overflow-hidden aspect-square rounded-2xl border-2 border-gray-300 shrink-0"
                  key={image}
                >
                  <Image src={image} alt="Uploaded image" fill />
                </div>
              ))}
            </div>
          </div>
        )}

        <span className="self-end mt-[-8px] pr-4 text-gray-400">
          {create_date}
        </span>
        {isModifyMode ? (
          <textarea
            name=""
            id=""
            placeholder={description}
            className="p-4 h-1/5 border-2 border-violet bg-gray-100 rounded-xl text-lg resize-none"
          ></textarea>
        ) : (
          <div className="p-4 h-1/6 flex flex-col gap-2 bg-gray-100 rounded-xl text-lg justify-self-end border-2 border-transparent">
            {description}
          </div>
        )}
      </section>
      {isDialogOpened && (
        <Dialog
          text="수정을 완료하시겠습니까?"
          closeModal={closeModal}
          handleConfirm={async () => onClickComplete()}
          loadingText="수정중입니다."
        />
      )}
      {errorText && <ToastMsg title={errorText} timer={2000} id={Date.now()} />}
    </div>
  );
}

/**
 *  <section className="mt-8">
        <div className="flex justify-between items-center">
          <span className="text-gray-900 text-2xl leading-5 py-2 pl-2">
            {place_name}
          </span>
         
        </div>
        <div className="text-gray-500 text-lg leading-5">{place_address}</div>
      </section>
 */
