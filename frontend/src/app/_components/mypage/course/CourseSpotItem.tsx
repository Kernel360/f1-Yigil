'use client';
import React, {
  Dispatch,
  HTMLAttributes,
  SetStateAction,
  forwardRef,
  useEffect,
  useState,
} from 'react';
import IconWithCounts from '../../IconWithCounts';
import ChevronDownIcon from '/public/icons/chevron-down.svg';
import StarIcon from '/public/icons/star.svg';
import ImageCarousel from '../../ui/carousel/ImageCarousel';
import XIcon from '/public/icons/x-mark.svg';
import Image from 'next/image';
import { EventFor } from '@/types/type';
import * as Common from '../detailModify';
import { TImageData } from '../../images/ImageHandler';
import ToastMsg from '../../ui/toast/ToastMsg';
import { TModifyCourse } from './CourseDetail';
import { getNumberMarker } from '../../add/course/util';

interface TItemProps extends HTMLAttributes<HTMLDivElement> {
  spot: TModifyCourse['spots'][0];
  spots: TModifyCourse['spots'];
  isModifyMode: boolean;
  setModifyCourse: Dispatch<SetStateAction<TModifyCourse>>;
  idx: number;
  animationStyle?: string;
  withOpacity?: boolean;
  isDragging?: boolean;
  onClickDeleteSpot: (e: EventFor<'span', 'onClick'>, id: number) => void;
}

const CourseSpotItem = forwardRef<HTMLDivElement, TItemProps>(
  (
    {
      spot,
      spots,
      isModifyMode,
      setModifyCourse,
      idx,
      onClickDeleteSpot,
      animationStyle,
      withOpacity,
      isDragging,
      ...props
    },
    ref,
  ) => {
    const {
      id,
      place_name,
      place_address,
      rate,
      create_date,
      image_url_list,
      order,
      description,
    } = spot;

    const [isOpen, setIsOpen] = useState(false);
    const [errorText, setErrorText] = useState('');
    const [selectOption, setSelectOption] = useState(rate);

    const onChangeSelectOption = (option: string) => {
      setSelectOption(option);

      const updatedSpot = { ...spot, rate: option };

      setModifyCourse((prev) => {
        const updatedSpots = prev.spots.map((prevSpot) =>
          prevSpot.order === order ? updatedSpot : prevSpot,
        );
        return { ...prev, spots: updatedSpots };
      });
    };

    const onChangeImages = (newImages: TImageData[]) => {
      const updatedSpot = { ...spot, image_url_list: newImages };

      setModifyCourse((prev) => {
        const updatedSpots = prev.spots.map((prevSpot) =>
          prevSpot.order === order ? updatedSpot : prevSpot,
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

    const changeOpenState = (e: EventFor<'button', 'onClick'>) => {
      e.stopPropagation();
      setIsOpen((prev) => !prev);
    };

    return (
      <article
        ref={ref}
        className={`flex flex-col bg-white ${
          withOpacity ? 'opacity-50' : 'opacity-100'
        } ${
          isDragging ? 'cursor-grabbing scale-105' : 'scale-100'
        } ${animationStyle}`}
        {...props}
      >
        <button
          className={`px-4 py-1 w-full border ${
            isModifyMode ? 'border-2 border-violet' : 'border-gray-500'
          } rounded-lg flex justify-between items-center`}
          onClick={changeOpenState}
        >
          <div className="flex items-center gap-x-2 py-2">
            <Image
              width={30}
              height={30}
              src={getNumberMarker(Number(idx + 1))}
              alt={`${order}번 마커`}
            />
            <span className="text-gray-700 text-xl leading-6">
              {place_name}
            </span>
            {isModifyMode && (
              <span
                className="bg-black p-1 rounded-full"
                onClick={(e) => onClickDeleteSpot(e, id)}
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
        {isOpen && !isDragging && (
          <section className="py-4 flex flex-col gap-4">
            <div className="flex justify-between items-center py-4 overflow-ellipsis">
              <div className="text-gray-500 font-semibold text-lg leading-5 break-keep">
                {place_address}
              </div>
              {isModifyMode ? (
                <Common.SelectContainer
                  selectOption={selectOption}
                  rate={rate}
                  onChangeSelectOption={onChangeSelectOption}
                  selectStyle="p-1"
                />
              ) : (
                <span className="shrink-0 text-gray-500">
                  <IconWithCounts
                    icon={
                      <StarIcon className="w-4 h-4 stroke-[#FACC15] fill-[#FACC15]" />
                    }
                    count={Number(rate)}
                    rating
                  />
                </span>
              )}
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
              <div className="h-32 p-4 rounded-xl bg-gray-100">
                {description}
              </div>
            )}
          </section>
        )}
        {errorText && (
          <ToastMsg title={errorText} timer={1000} id={Date.now()} />
        )}
      </article>
    );
  },
);
CourseSpotItem.displayName = 'CourseSpotItem';

export default CourseSpotItem;
