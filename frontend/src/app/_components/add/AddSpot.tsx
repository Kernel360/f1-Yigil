'use client';

import { useReducer, useState } from 'react';

import { makeInitialStep, reducer, StepNavigation } from './common/step';
import ProgressIndicator from './common/ProgressIndicator';
import {
  AddSpotContext,
  addSpotReducer,
  initialAddSpotState,
} from './spot/SpotContext';

import * as Common from './common';

import SearchBox from '../search';
import ImageHandler from '../images';
import MapComponent from '../naver-map/MapComponent';

import type { DataInput, Making } from './common/step/types';

import { searchAction } from '../search/action';

export default function AddSpot() {
  const [step, dispatchStep] = useReducer(
    reducer,
    makeInitialStep(true, false),
  );

  const [addSpotState, dispatchSpot] = useReducer(
    addSpotReducer,
    initialAddSpotState,
  );

  const [searchResults, setSearchResults] = useState<
    { name: string; roadAddress: string }[]
  >([]);

  const { makingStep, inputStep } = step;

  const makingSpotStep = makingStep as Making.TSpot;
  const dataFromNewStep = inputStep as DataInput.TDataFromNew;

  const stepLabel = makingSpotStep.data.label;
  const inputLabel = dataFromNewStep.data.label;

  async function search(keyword: string) {
    if (keyword === '') {
      setSearchResults([]);
      return;
    }

    const results = await searchAction(keyword);

    if (results.status === 'succeed') {
      setSearchResults(results.data);
    }
  }

  // 검색을 통해 선택한 장소
  const [currentFoundPlace, setCurrentFoundPlace] = useState<{
    name: string;
    roadAddress: string;
    coords: { lat: number; lng: number };
  }>();

  return (
    <section className="flex flex-col grow relative">
      <AddSpotContext.Provider value={addSpotState}>
        <StepNavigation
          currentStep={step}
          next={() => dispatchStep({ type: 'next' })}
          previous={() => dispatchStep({ type: 'previous' })}
        />
        <ProgressIndicator step={step} />

        {stepLabel === '장소 입력' && (
          <section className="flex flex-col justify-between grow">
            <SearchBox
              searchResults={searchResults}
              search={search}
              setCurrentFoundPlace={setCurrentFoundPlace}
            />
            <div className="absolute w-full h-full">
              <MapComponent width="100%" height="100%">
                <Common.AddSpotMap
                  place={currentFoundPlace}
                  dispatchStep={dispatchStep}
                  dispatchSpot={dispatchSpot}
                />
              </MapComponent>
            </div>
          </section>
        )}
        {stepLabel === '정보 입력' && (
          <>
            {inputLabel === '시작' && <></>}
            {inputLabel === '주소' && (
              <>
                <Common.InfoTitle
                  label={inputLabel}
                  additionalLabel="를 확인하세요"
                />
                <Common.AddPlaceInfo />
              </>
            )}
            {inputLabel === '사진' && (
              <>
                <Common.InfoTitle
                  label={inputLabel}
                  additionalLabel="을 업로드하세요"
                />
                <ImageHandler dispatch={dispatchSpot} size={5} />
              </>
            )}
            {inputLabel === '별점' && (
              <>
                <Common.InfoTitle
                  label={inputLabel}
                  additionalLabel="을 매기세요"
                />
                <Common.PostRating dispatch={dispatchSpot} />
              </>
            )}
            {inputLabel === '리뷰' && (
              <>
                <Common.InfoTitle
                  label={inputLabel}
                  additionalLabel="를 남기세요"
                />
                <Common.PostReview dispatch={dispatchSpot} />
              </>
            )}
          </>
        )}
        {stepLabel === '장소 확정' && <Common.SpotCheck />}
        {stepLabel === '완료' && <Common.AddConfirmContent />}
      </AddSpotContext.Provider>
    </section>
  );
}
