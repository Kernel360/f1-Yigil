'use client';
import { TMyPageCourseDetail } from '@/types/myPageResponse';
import React, { useState } from 'react';
import IconWithCounts from '../../IconWithCounts';
import StarIcon from '/public/icons/star.svg';
import Image from 'next/image';
import PlaceholderImage from '/public/images/placeholder.png';
import CourseSpots from './CourseSpots';
import BackButton from '../../place/BackButton';
import Dialog from '../../ui/dialog/Dialog';
import ToastMsg from '../../ui/toast/ToastMsg';
import * as Common from '../detailModify';
import { patchCourseDetail } from '../hooks/myPageActions';

export interface TModifyCourse {
  title: string;
  description: string;
  rate: string;
  spotIdOrder: string[];
  spots: {
    place_name: string;
    create_date: string;
    order: string;
    description: string;
    rate: string;
    image_url_list: { filename: string; uri: string }[];
  }[];
}

export default function CourseDetail({
  courseDetail,
  courseId,
}: {
  courseDetail: TMyPageCourseDetail;
  courseId: number;
}) {
  const { title, rate, spots, map_static_image_url, description } =
    courseDetail;

  const [isModifyMode, setIsModifyMode] = useState(false);
  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [selectOption, setSelectOption] = useState(Number(rate).toFixed(1));
  const [modifyCourse, setModifyCourse] = useState<TModifyCourse>({
    title,
    rate,
    description,
    spotIdOrder: spots.map((spot) => spot.order),
    spots: spots.map((spot) => ({
      ...spot,
      image_url_list: spot.image_url_list.map((image, idx) => ({
        filename: idx.toString(),
        uri: image,
      })),
    })),
  });

  const closeModal = () => {
    setIsDialogOpened(false);
  };

  const onChangeTitle = (title: string) => {
    setModifyCourse((prev) => ({ ...prev, title }));
  };

  const onChangeSelectOption = (option: string | number) => {
    if (typeof option === 'number') return;
    setSelectOption(option);
  };

  const onClickComplete = () => {
    setIsDialogOpened(false);
    setIsModifyMode(false);
    patchCourseDetail(courseId, modifyCourse);
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
      <section className="px-4 flex flex-col grow">
        {isModifyMode ? (
          <div className="flex w-full py-4">
            <Common.TitleInput
              title={modifyCourse.title}
              onChangeTitle={onChangeTitle}
            />
            <div className="grow"></div>
            <Common.SelectContainer
              selectOption={selectOption}
              rate={rate}
              onChangeSelectOption={onChangeSelectOption}
            />
          </div>
        ) : (
          <div className="px-2 py-4">
            <div className="flex justify-between">
              <span className="text-2xl font-semibold border-2 border-transparent">
                {title}
              </span>
              <span className="flex items-center">
                <IconWithCounts
                  icon={
                    <StarIcon className="w-4 h-4 stroke-[#FACC15] fill-[#FACC15]" />
                  }
                  count={Number(rate)}
                  rating
                />
              </span>
            </div>
          </div>
        )}
        <CourseSpots
          spots={modifyCourse.spots}
          setModifyCourse={setModifyCourse}
          isModifyMode={isModifyMode}
        />
        <div className="my-4 relative aspect-video">
          <Image
            className="rounded-xl object-cover"
            priority
            src={map_static_image_url}
            placeholder="blur"
            blurDataURL={PlaceholderImage.blurDataURL}
            alt={`${title} 경로 이미지`}
            fill
          />
        </div>
        <span className="self-end text-gray-400 font-medium">
          {new Date(Date.now()).toLocaleDateString()}
        </span>
        <div className="my-4 h-36">
          <div className="h-full p-4 rounded-xl bg-gray-100">{description}</div>
        </div>
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
