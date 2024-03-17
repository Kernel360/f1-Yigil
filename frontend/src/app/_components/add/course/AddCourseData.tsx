'use client';

import { useContext, useState } from 'react';
import { StepContext } from '@/context/travel/step/StepContext';
import { CourseContext } from '@/context/travel/course/CourseContext';

import AddCoursePlace from './AddCoursePlace';
import AddCourseImages from './AddCourseImages';
import AddCourseReview from './AddCourseReviews';
import AddCourseConfirm from './AddCourseConfirm';
import SetMethodButton from './SetMethodButton';

export default function AddCourseData() {
  const [step] = useContext(StepContext);
  const [, dispatchCourse] = useContext(CourseContext);
  const [method, setMethod] = useState<1 | 2>(1);

  function onSelectMethod() {
    switch (method) {
      case 1: {
        setMethod(2);
        break;
      }
      case 2: {
        setMethod(1);
        break;
      }
    }

    dispatchCourse({ type: 'INIT_COURSE' });
  }

  const { value } = step.data;

  switch (value) {
    case 0:
      return (
        <section className="p-4 flex flex-col grow justify-center items-center gap-4">
          <SetMethodButton
            title="장소도 함께 기록하기"
            description="아직 기록하지 못한 장소와 함께\n 코스를 기록해보세요."
            selected={method === 1}
            onSelect={onSelectMethod}
          />
          <SetMethodButton
            title="일정만 기록하기"
            description="미리 기록한 장소로\n코스를 기록해 보세요."
            selected={method === 2}
            onSelect={onSelectMethod}
          />
        </section>
      );
    case 1:
      return <AddCoursePlace method={method} />;
    case 2:
      return <AddCourseImages />;
    case 3:
      return <AddCourseReview />;
    case 4:
      return <AddCourseConfirm />;
  }
}
