import React, { useEffect, useState } from 'react';
import { TMyPageCourse } from '@/types/myPageResponse';
import CourseItem from '../CourseItem';

interface TMyPageCourseItem extends TMyPageCourse {
  idx: number;
  checkedList: { course_id: number; is_private: boolean }[];
  onChangeCheckedList: (course_id: number, is_private: boolean) => void;
  selectOption: string;
}

export default function MyPageCourseItem({
  map_static_image_url,
  course_id,
  title,
  is_private,
  created_date,
  rate,
  spot_count,
  idx,
  checkedList,
  onChangeCheckedList,
  selectOption,
}: TMyPageCourseItem) {
  const [isCheckDisabled, setIsCheckDisabled] = useState(false);
  const [isChecked, setIsChecked] = useState(false);

  useEffect(() => {
    const found = checkedList.find(
      (checked) => checked.course_id === course_id,
    );

    if (found) setIsChecked(true);
    else setIsChecked(false);
  }, [checkedList, course_id]);

  useEffect(() => {
    if (selectOption === 'all' && is_private) {
      setIsCheckDisabled(true);
      setIsChecked(false);
    }
  }, [selectOption, checkedList.length, is_private]); // 전체 선택 및 해제 시에 disabled 풀리는 현상
  return (
    <div
      className={`flex items-center px-5 py-4 border-b-2 gap-x-4 ${
        idx === 0 && 'border-t-2'
      }`}
    >
      <input
        type="checkbox"
        disabled={isCheckDisabled}
        className="w-[32px] h-[32px]"
        checked={isChecked}
        onChange={() => {
          onChangeCheckedList(course_id, is_private);
        }}
      />
      <CourseItem
        map_static_image_url={map_static_image_url}
        is_private={is_private}
        course_id={course_id}
        title={title}
        rate={rate}
        spot_count={spot_count}
        created_date={created_date}
      />
    </div>
  );
}
