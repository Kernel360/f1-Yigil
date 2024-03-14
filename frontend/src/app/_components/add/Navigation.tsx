'use client';

import { useContext } from 'react';
import { useRouter } from 'next/navigation';

import { StepContext } from '@/context/travel/step/StepContext';
import { SpotContext } from '@/context/travel/spot/SpotContext';
import { CourseContext } from '@/context/travel/course/CourseContext';
import { isDefaultPlace } from '@/context/travel/place/reducer';

export default function Navigation() {
  const [step, dispatch] = useContext(StepContext);
  const [spot, dispatchSpot] = useContext(SpotContext);
  const [course, dispatchCourse] = useContext(CourseContext);

  const { back } = useRouter();

  return (
    <nav className="mx-4 flex justify-between items-center">
      <button
        onClick={() => {
          // Spot 추가 / 장소 입력 단계일 때
          if (step.type === 'spot' && step.data.value === 1) {
            if (!isDefaultPlace({ type: 'spot', data: spot.place })) {
              dispatchSpot({ type: 'INIT_SPOT' });
            } else {
              back();
            }

            return;
          }

          // Course 추가 / 장소 입력 단계일 때
          if (step.type === 'course' && step.data.value === 1) {
            const places = course.spots.map((spot) => spot.place);

            if (!isDefaultPlace({ type: 'course', data: places })) {
              dispatchCourse({ type: 'INIT_COURSE' });
              return;
            }
          }

          // Spot 추가 / 방식 선택 단계일 때
          if (step.data.value === 0) {
            back();
            return;
          }

          dispatch({ type: 'PREVIOUS' });
        }}
      >
        이전
      </button>
      <span className="text-xl text-semibold text-gray-900">
        {step.data.label}
      </span>
      <button onClick={() => dispatch({ type: 'NEXT' })}>다음</button>
    </nav>
  );
}
