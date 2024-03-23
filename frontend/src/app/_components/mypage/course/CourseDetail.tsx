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
import MaximizeIcon from '/public/icons/maximize.svg';
import CourseConfirmMap from './CourseConfirmMap';
import { scrollToTop } from '@/utils';
import { getCoords } from '../../search/action';
import { getCourseStaticMap, getRouteGeoJson } from '../../add/course/action';
import { TCoords, TLineString } from '@/context/travel/schema';
import { EventFor } from '@/types/type';

export interface TModifyCourse {
  title: string;
  description: string;
  rate: string;
  spotIdOrder: number[];
  line_string_json: TLineString;
  map_static_image_url: string;
  spots: {
    id: number;
    place_name: string;
    place_address: string;
    create_date: string;
    order: string;
    description: string;
    rate: number;
    image_url_list: { filename: string; uri: string }[];
  }[];
}

export default function CourseDetail({
  courseDetail,
  courseId,
  isLoggedIn,
}: {
  courseDetail: TMyPageCourseDetail;
  courseId: number;
  isLoggedIn: boolean;
}) {
  const {
    title,
    rate,
    spots,
    map_static_image_url,
    description,
    line_string_json,
  } = courseDetail;

  const [isMapOpen, setIsMapOpen] = useState(false);
  const [isModifyMode, setIsModifyMode] = useState(false);
  const [isDialogOpened, setIsDialogOpened] = useState(false);
  const [errorText, setErrorText] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const [coordsList, setCoordsList] = useState<TCoords[]>([]);
  const [modifyCourse, setModifyCourse] = useState<TModifyCourse>({
    title,
    rate: rate.toFixed(1),
    description,
    line_string_json,
    map_static_image_url,
    spotIdOrder: spots.map((spot) => spot.id),
    spots: spots.map((spot) => ({
      ...spot,
      image_url_list: spot.image_url_list.map((image, idx) => ({
        filename: idx.toString(),
        uri: image,
      })),
    })),
  });

  const closeMap = () => {
    setIsMapOpen(false);
  };

  const closeModal = () => {
    setIsDialogOpened(false);
  };

  const onChangeDescription = (description: string) => {
    if (description.length > 30) {
      setErrorText('리뷰를 30자 이내로 입력해야 합니다.');
      return;
    }
    setModifyCourse((prev) => ({ ...prev, description }));
    setErrorText('');
  };

  const onChangeTitle = (title: string) => {
    if (!title.trim()) {
      setErrorText('코스 제목을 입력해주세요');
      return;
    }
    setModifyCourse((prev) => ({ ...prev, title }));
  };

  const onChangeSelectOption = (option: string | number) => {
    if (typeof option === 'number') return;
    setModifyCourse((prev) => ({ ...prev, rate: option }));
  };

  const onClickComplete = async () => {
    setErrorText('');
    setIsDialogOpened(false);

    if (
      courseDetail.spots.some(
        (spot) => !spot.image_url_list.length || !spot.description.trim(),
      )
    ) {
      setErrorText('각 장소에 사진이나 리뷰가 필요합니다.');
      return;
    }
    await getCoordsList(modifyCourse.spots);
    setIsModifyMode(false);
    await patchCourseDetail(courseId, modifyCourse);
  };

  const getCoordsList = async (spots: TModifyCourse['spots']) => {
    try {
      setIsLoading(true);
      const res = spots.map((spot) => getCoords(spot.place_address));
      const results = await Promise.allSettled(res);
      const contents = results
        .filter(
          (result) =>
            result.status === 'fulfilled' && result.value.status === 'succeed',
        )
        .map(
          (result) =>
            (
              result as PromiseFulfilledResult<{
                status: 'succeed';
                content: { lng: number; lat: number };
              }>
            ).value.content,
        );
      const staticImage = await getCourseStaticMap(contents);
      if (staticImage.status === 'failed') {
        setErrorText(staticImage.message);
        return;
      }
      setModifyCourse((prev) => ({
        ...prev,
        map_static_image_url: staticImage.dataUrl,
      }));
      setCoordsList(contents);
      getLineString(contents);
    } catch (error) {
      if (error instanceof Error) {
        setErrorText(error.message);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const getLineString = async (coords: TCoords[]) => {
    const lineString = await getRouteGeoJson(coords);
    const parsedJson = lineString.status === 'success' && lineString.data;
    setModifyCourse((prev) => ({
      ...prev,
      line_string_json: parsedJson as TLineString,
    }));
  };

  const changedSpotIdOrder = async (spotIdOrder: number[]) => {
    setModifyCourse((prev) => ({
      ...prev,
      spotIdOrder,
    }));
  };

  const onClickDeleteSpot = (e: EventFor<'span', 'onClick'>, id: number) => {
    e.stopPropagation();
    if (spots.length === 2) {
      setErrorText('코스는 2개 이상 장소가 존재해야 합니다.');
      return;
    }
    const filteredSpot = modifyCourse.spots.filter((spot) =>
      modifyCourse.spotIdOrder.includes(spot.id),
    );
    setModifyCourse((prev) => ({ ...prev, spots: filteredSpot }));
    setErrorText('');
  };

  return (
    <div className="mx-4 touch-none">
      <nav className="relative py-4 flex justify-between items-center">
        <BackButton />
        <span className="text-2xl font-light">
          {isModifyMode ? '기록 수정' : '장소 기록'}
        </span>
        {isLoggedIn ? (
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
        ) : (
          <div></div>
        )}
      </nav>
      <section className="relative h-screen">
        <div className="flex flex-col px-4">
          {isModifyMode ? (
            <div className="flex w-full py-4">
              <Common.TitleInput
                title={modifyCourse.title}
                onChangeTitle={onChangeTitle}
              />
              <div className="grow"></div>
              <Common.SelectContainer
                selectOption={modifyCourse.rate}
                rate={modifyCourse.rate}
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
            changedSpotIdOrder={changedSpotIdOrder}
            onClickDeleteSpot={onClickDeleteSpot}
          />
          <div className="my-4 relative aspect-video">
            <Image
              className="rounded-xl object-cover"
              priority
              src={modifyCourse.map_static_image_url}
              placeholder="blur"
              blurDataURL={PlaceholderImage.blurDataURL}
              alt={`${title} 경로 이미지`}
              fill
              sizes="100vw"
            />
            <button
              onClick={() => {
                scrollToTop();
                setIsMapOpen(true);
              }}
              disabled={isLoading}
            >
              <MaximizeIcon className="absolute right-1 top-1" />
            </button>
          </div>
          <span className="self-end text-gray-400 font-medium">
            {new Date(Date.now()).toLocaleDateString()}
          </span>
          <div className="my-4 flex flex-col gap-4">
            {isModifyMode ? (
              <Common.TextArea
                description={modifyCourse.description}
                onChangeHandler={onChangeDescription}
              />
            ) : (
              <div className="h-36 p-4 rounded-xl bg-gray-100">
                {description}
              </div>
            )}
          </div>
        </div>
        {isMapOpen && (
          <div className="absolute top-0 w-full h-full ">
            <CourseConfirmMap
              lineString={modifyCourse.line_string_json.coordinates}
              spotAddresses={coordsList}
              close={closeMap}
            />
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
