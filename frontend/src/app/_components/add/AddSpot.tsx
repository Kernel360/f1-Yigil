'use client';

import { useReducer, useState } from 'react';

import { makeInitialStep, reducer, StepNavigation } from './common/step';
import ProgressIndicator from './common/ProgressIndicator';
import {
  AddSpotContext,
  addSpotReducer,
  initialAddSpotState,
} from './spot/SpotContext';

import type { DataInput, Making } from './common/step/types';

import SearchBox from '../search';
import SpotCheck from './common/SpotCheck';
import InfoTitle from './common/InfoTitle';
import AddPlaceInfo from './common/AddPlaceInto';
import { ImageHandler } from '../images';
import PostRating from './common/PostRating';
import PostReview from './common/PostReview';
import AddSpotMap from './common/AddSpotMap';
import MapComponent from '../naver-map/MapComponent';

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

    const res = await fetch('http://localhost:3000/endpoints/api/search', {
      method: 'POST',
      body: JSON.stringify({ keyword }),
    });

    const areas = (await res.json()) as any[];

    const results = areas.map((area) => {
      const title = area.title as string;
      const escaped = title.replace(/<b>|<\/b>/g, '');
      const roadAddress = area.roadAddress as string;

      return { name: escaped, roadAddress };
    });

    setSearchResults(results);
  }

  return (
    <section className="flex flex-col grow">
      <AddSpotContext.Provider value={addSpotState}>
        <StepNavigation
          currentStep={step}
          next={() => dispatchStep({ type: 'next' })}
          previous={() => dispatchStep({ type: 'previous' })}
        />
        <ProgressIndicator step={step} />

        {stepLabel === '장소 입력' && (
          <section className=" flex flex-col justify-between grow">
            <SearchBox
              dispatchSpot={dispatchSpot}
              dispatchStep={dispatchStep}
              searchResults={searchResults}
              search={search}
            />
            <hr className="my-2 pb-1 align-bottom" />
            <MapComponent width="100%" height="100%">
              <AddSpotMap
                title={addSpotState.name}
                coords={addSpotState.coords}
              />
            </MapComponent>
          </section>
        )}
        {stepLabel === '정보 입력' && (
          <>
            {inputLabel === '시작' && <></>}
            {inputLabel === '주소' && (
              <>
                <InfoTitle label={inputLabel} additionalLabel="를 확인하세요" />
                <AddPlaceInfo />
              </>
            )}
            {inputLabel === '사진' && (
              <>
                <InfoTitle
                  label={inputLabel}
                  additionalLabel="을 업로드하세요"
                />
                <ImageHandler dispatch={dispatchSpot} size={5} />
              </>
            )}
            {inputLabel === '별점' && (
              <>
                <InfoTitle label={inputLabel} additionalLabel="을 매기세요" />
                <PostRating dispatch={dispatchSpot} />
              </>
            )}
            {inputLabel === '리뷰' && (
              <>
                <InfoTitle label={inputLabel} additionalLabel="를 남기세요" />
                <PostReview dispatch={dispatchSpot} />
              </>
            )}
          </>
        )}
        {stepLabel === '장소 확정' && <SpotCheck />}
      </AddSpotContext.Provider>
    </section>
  );
}
