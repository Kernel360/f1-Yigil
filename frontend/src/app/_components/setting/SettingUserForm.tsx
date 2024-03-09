import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction, useRef, useState } from 'react';
import { ages } from './contants';
import { TModifyUser } from './ModifyUser';
import StarIcon from '/public/icons/star.svg';
import Link from 'next/link';
import { checkIsExistNickname } from './actions';

export interface TProps {
  userForm: TModifyUser;
  setUserForm: Dispatch<SetStateAction<TModifyUser>>;
  openModal: () => void;
}

export default function SettingUserForm({
  userForm,
  setUserForm,
  openModal,
}: TProps) {
  const [nicknameValidation, setNicknameValidation] = useState(false);
  const [completeBtnValidation, setCompleteBtnValidation] = useState(false);
  const [errorText, setErrorText] = useState('');
  const nicknameRef = useRef<HTMLInputElement>(null);

  const onChangeInput = (e: EventFor<'input', 'onChange'>) => {
    const { value, name } = e.target;
    setUserForm({ ...userForm, [name]: value });
  };

  const onClickInput = (e: EventFor<'input', 'onClick'>) => {
    const { value, name } = e.currentTarget;
    setUserForm({ ...userForm, [name]: value });
  };

  const compareNicknameIsExist = async () => {
    console.log('blured');
    if (validationNickname(userForm.nickname)) {
      // const nicknameCheck = await checkIsExistNickname(userForm.nickname);
    }
    if (!nicknameValidation) nicknameRef.current?.focus();
  };

  const deleteInterestedArea = () => {
    // 관심지역 delete 로직
  };

  const regions = [
    { id: 1, name: '서울' },
    { id: 2, name: '대전' },
    { id: 3, name: '대구' },
    { id: 4, name: '부산' },
    { id: 5, name: '찍고' },
  ];

  // const onClickArea = (e: EventFor<'input', 'onClick'>) => {
  //   if (userForm?.area?.includes(e.currentTarget.value)) {
  //     const popArea = userForm?.area.filter((i) => i !== e.currentTarget.value);
  //     setUserForm({ ...userForm, area: popArea });
  //   } else {
  //     const addedArea = userForm?.area && [
  //       ...userForm.area,
  //       e.currentTarget.value,
  //     ];
  //     setUserForm({
  //       ...userForm,
  //       area: addedArea,
  //     });
  //   }
  // };

  return (
    <section className="mx-4">
      <label htmlFor="name" className="flex flex-col">
        <div className="flex justify-between items-center my-2  ">
          <span className="text-xl leading-5 text-gray-700">닉네임(필수)</span>
          <span className="text-gray-500">
            {userForm?.nickname?.length} / 20
          </span>
        </div>
        <input
          ref={nicknameRef}
          id="name"
          type="text"
          name="nickname"
          placeholder="닉네임"
          value={userForm.nickname}
          className={`border-[1px] px-4 py-2 text-2xl rounded-md outline-gray-300 ${
            nicknameValidation ? 'border-main' : 'border-red'
          }`}
          onChange={onChangeInput}
          onBlur={compareNicknameIsExist}
        />
      </label>
      {!nicknameValidation && (
        <div className="text-main">이미 사용중인 닉네임입니다.</div>
      )}

      <section className="flex flex-col">
        <span className="text-gray-700 my-2">성별</span>
        <div className="flex items-center text-gray-300 text-2xl text-center gap-x-2">
          <input
            type="button"
            value={'남성'}
            name="gender"
            className={`grow  rounded-md py-2 cursor-pointer ${
              userForm?.gender === '남성'
                ? 'border-main text-main border-[1px] font-semibold'
                : 'border-gray-300 border-[1px]'
            }`}
            onClick={onClickInput}
          />
          <input
            type="button"
            value={'여성'}
            name="gender"
            className={`grow border-gray-300 border-[1px] rounded-md py-2 cursor-pointer ${
              userForm?.gender === '여성'
                ? 'border-main text-main border-[1px] font-semibold'
                : 'border-gray-300 border-[1px]'
            }`}
            onClick={onClickInput}
          />
        </div>
      </section>
      <section className="flex flex-col">
        <span className="text-gray-700 my-2">나이</span>
        <div className="grid grid-cols-3 gap-3">
          {ages.map((age) => (
            <input
              key={age}
              type="button"
              value={age}
              name="age"
              className={`border-[1px] py-3 text-xl text-gray-300 rounded-md cursor-pointer
              ${
                userForm?.age === age
                  ? 'border-main text-main border-[1px] font-semibold'
                  : 'border-gray-300 border-[1px]'
              }`}
              onClick={onClickInput}
            />
          ))}
        </div>
      </section>
      <section className="flex flex-col">
        <span className="text-gray-700 my-2">관심 지역(선택)</span>
        <ul className="grid grid-cols-2 gap-x-2 gap-y-4">
          {Array.from(Array(5)).map((area, idx) =>
            idx < regions.length ? (
              <li
                key={regions[idx].id}
                className="flex justify-center items-center gap-x-2 text-xl text-main font-semibold border-[1px] border-main py-4 leading-5 rounded-md cursor-pointer"
                onClick={deleteInterestedArea}
              >
                <div>{regions[idx].name}</div>
                <StarIcon className="w-4 h-4 fill-[#FACC15] stroke-[#FACC15]" />
              </li>
            ) : (
              <li
                key={idx}
                className="flex justify-center items-center gap-x-2 text-xl text-gray-300 font-semibold border-[1px] border-gray-300 py-4 leading-5 rounded-md"
              >
                <div>지역</div>
                <StarIcon className="w-4 h-4 stroke-gray-300" />
              </li>
            ),
          )}
          <Link
            href="/area"
            className="flex justify-center items-center gap-x-2 text-xl text-gray-500 bg-gray-200 font-semibold border-[1px] border-gray-300 py-4 leading-5 rounded-md"
          >
            지역 선택
          </Link>
        </ul>
      </section>
      <div className="pb-8 mt-[60px] mx-4 flex justify-center items-center text-white text-2xl leading-7 font-semibold">
        <button
          className={`w-full py-4 rounded-md ${
            completeBtnValidation ? 'bg-main' : 'bg-gray-200'
          }`}
          // disabled={!completeBtnValidation}
          onClick={openModal}
        >
          완료
        </button>
      </div>
    </section>
  );
}

function validationNickname(nickname: string) {
  const regExp = /^[a-zA-Z0-9ㄱ-힣][a-zA-Z0-9ㄱ-힣 ]{1,19}$/;
  return regExp.test(nickname);
}
