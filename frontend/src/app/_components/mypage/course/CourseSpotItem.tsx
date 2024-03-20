'use client';
import React, { Dispatch, SetStateAction, useEffect, useState } from 'react';
import IconWithCounts from '../../IconWithCounts';
import ChevronDownIcon from '/public/icons/chevron-down.svg';
import StarIcon from '/public/icons/star.svg';
import ImageCarousel from '../../ui/carousel/ImageCarousel';
import XIcon from '/public/icons/x-mark.svg';
import Image from 'next/image';
import PlaceholderImage from '/public/images/placeholder.png';
import { EventFor } from '@/types/type';
import * as Common from '../detailModify';
import { TImageData } from '../../images/ImageHandler';
import ToastMsg from '../../ui/toast/ToastMsg';
import { getNumberMarker } from '../../add/course/AddCourseMap';
import { TModifyCourse } from './CourseDetail';

export default function CourseSpotItem({
  spot,
  spots,
  isModifyMode,
  setModifyCourse,
}: {
  spot: TModifyCourse['spots'][0];
  spots: TModifyCourse['spots'];
  isModifyMode: boolean;
  setModifyCourse: Dispatch<SetStateAction<TModifyCourse>>;
}) {
  const { place_name, rate, create_date, image_url_list, order, description } =
    spot;

  const [isOpen, setIsOpen] = useState(false);
  const [errorText, setErrorText] = useState('');

  const onChangeImages = (newImages: TImageData[]) => {
    const updatedSpot = { ...spot, image_url_list: newImages };
    setModifyCourse((prev) => {
      const updatedSpots = prev.spots.map((s) =>
        s.order === order ? updatedSpot : s,
      );
      return { ...prev, spots: updatedSpots };
    });
  };

  const onChangeDescription = (title: string) => {
    if (description.length > 30) {
      setErrorText('리뷰를 30자 이내로 입력해야 합니다.');
      return;
    } else {
      const updatedSpot = { ...spot, description: title };
      setModifyCourse((prev) => {
        const updatedSpots = prev.spots.map((spot) =>
          spot.order === order ? updatedSpot : spot,
        );
        return { ...prev, spots: updatedSpots };
      });
      setErrorText('');
    }
  };

  const onClickDeleteSpot = (e: EventFor<'span', 'onClick'>, order: string) => {
    e.stopPropagation();
    if (spots.length === 2) {
      setErrorText('코스는 2개 이상 장소가 존재해야 합니다.');
      return;
    }
    setModifyCourse((prev) => {
      const filterSpots = prev.spots.filter(
        (prevSpot) => prevSpot.order !== order,
      );
      return {
        ...prev,
        spots: filterSpots,
      };
    });
    setErrorText('');
  };

  return (
    <article className="flex flex-col">
      <button
        className={`pr-4 py-1 w-full border ${
          isModifyMode ? 'border-2 border-violet' : 'border-gray-500'
        } rounded-lg flex justify-between items-center`}
        onClick={() => setIsOpen(!isOpen)}
      >
        <div className="flex items-center gap-x-2">
          <Image
            width={36}
            height={36}
            src={getNumberMarker(Number(order))}
            alt={`${order}번 마커`}
            className="pb-2 ml-2"
          />
          <span className="text-gray-700 text-xl leading-6">{place_name}</span>
          {isModifyMode && (
            <span
              className="bg-black p-1 rounded-full"
              onClick={(e) => onClickDeleteSpot(e, order)}
            >
              {<XIcon className="w-3 h-3 z-30 stroke-white" />}
            </span>
          )}
        </div>
        <span>
          <ChevronDownIcon
            className={`w-4 h-4 stroke-gray-500 stroke-2 [stroke-linecap:round] [stroke-linejoin:round] ${
              isOpen && 'rotate-180'
            }`}
          />
        </span>
      </button>
      {isOpen && (
        <section className="py-4 flex flex-col gap-4">
          <div className="px-4 flex justify-between items-center">
            <span className="text-gray-500 font-medium">지역 어드레스~</span>
            <span className="pl-4 shrink-0">
              <IconWithCounts
                icon={
                  <StarIcon className="w-4 h-4 stroke-[#FACC15] fill-[#FACC15]" />
                }
                count={Number(rate)}
                rating
              />
            </span>
          </div>
          {isModifyMode ? (
            <Common.ModifyImage
              image_urls={spot.image_url_list}
              setImages={onChangeImages}
              order={order}
            />
          ) : (
            <div className="pl-2">
              <ImageCarousel
                images={image_url_list.map((image) => image.uri)}
                label=""
                variant="thumbnail"
              />
            </div>
          )}
          <span className="self-end text-gray-400 font-medium">
            {create_date}
          </span>

          {isModifyMode ? (
            <Common.TextArea
              description={description}
              onChangeHandler={onChangeDescription}
            />
          ) : (
            <div className="h-32 p-4 rounded-xl bg-gray-100">{description}</div>
          )}
        </section>
      )}
      {errorText && <ToastMsg title={errorText} timer={1000} id={Date.now()} />}
    </article>
  );
}
