'use client';

import { useContext } from 'react';
import { CourseWithNewStepContext } from '@/context/travel/step/course/CourseWithNewStepContext';
import { CourseWithoutNewStepContext } from '@/context/travel/step/course/CourseWithoutNewStepContext';

import AddCourseNewPlace from './AddCourseNewPlace';
import AddCourseExistSpots from './AddCourseExistSpots';
import AddCourseImages from './AddCourseImages';
import AddCourseReview from './AddCourseReviews';
import AddCourseConfirm from './AddCourseConfirm';
import SetMethodButton from './SetMethodButton';
import AddCourseOrder from './AddCourseOrder';

export default function AddCourseData({
  method,
  onSelect,
}: {
  method: 'with-new' | 'without-new';
  onSelect: (nextMethod: 'with-new' | 'without-new') => void;
}) {
  const [withNew] = useContext(CourseWithNewStepContext);
  const [withoutnew] = useContext(CourseWithoutNewStepContext);

  const { value } = method === 'with-new' ? withNew : withoutnew;

  switch (value) {
    case 0: {
      return (
        <section className="p-4 flex flex-col grow justify-center items-center gap-4">
          <SetMethodButton
            title="장소도 함께 기록하기"
            description="아직 기록하지 못한 장소와 함께\n 코스를 기록해보세요."
            selected={method === 'with-new'}
            onSelect={() => onSelect('with-new')}
          />
          <SetMethodButton
            title="일정만 기록하기"
            description="미리 기록한 장소로\n코스를 기록해 보세요."
            selected={method === 'without-new'}
            onSelect={() => onSelect('without-new')}
          />
        </section>
      );
    }
    case 1: {
      if (method === 'with-new') {
        return <AddCourseNewPlace />;
      }

      return <AddCourseExistSpots />;
    }
    case 2: {
      if (method === 'with-new') {
        return <AddCourseImages />;
      }

      return <AddCourseOrder />;
    }
    case 3:
      return <AddCourseReview disabled={method === 'without-new'} />;
    case 4:
      return <AddCourseConfirm />;
  }
}
