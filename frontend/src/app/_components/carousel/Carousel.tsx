import React from 'react';
/**
 * @TODO: 이미지 배열을 받아 보여준다.
 * @TODO: 첫 번째 이미지일 경우 next 버튼만 활성화, 마지막 이미지일 경우 prev 버튼만 활성화 -> 현재 보여주는 사진 인덱스가 배열의 length - 1과 같다면 next disable, 인덱스가 0이라면 prev disable
 * @TODO:
 */

interface PropsType {
  imgs: string[];
}
export default function Carousel({ imgs }: PropsType) {
  return <div>Carousel</div>;
}
