import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction, useState } from 'react';
import { ages } from './contants';
import { UserType } from './UserModifyForm';

interface PropsType {
  userForm: UserType | undefined;
  setUserForm: Dispatch<SetStateAction<UserType | undefined>>;
}

export default function SettingUserForm({ userForm, setUserForm }: PropsType) {
  const onChangeInput = (e: EventFor<'input', 'onChange'>) => {
    const { value, name } = e.target;
    setUserForm({ ...userForm, [name]: value });
  };

  const onClickInput = (e: EventFor<'input', 'onClick'>) => {
    const { value, name } = e.currentTarget;
    setUserForm({ ...userForm, [name]: value });
  };

  return (
    <div className="mx-4">
      <label htmlFor="name" className="flex flex-col">
        <span className="text-gray-500 my-2">닉네임</span>
        <input
          id="name"
          type="text"
          name="name"
          placeholder="닉네임"
          value={userForm?.name || ''}
          className="border-2 px-4 py-2 text-2xl rounded-md"
          onChange={onChangeInput}
        />
      </label>

      <label className="flex flex-col">
        <span className="text-gray-500 my-2">성별</span>
        <div className="flex items-center text-gray-300 text-2xl text-center gap-x-2">
          <input
            type="button"
            value={'남성'}
            name="gender"
            className={`grow  rounded-md py-2 cursor-pointer ${
              userForm?.gender && userForm.gender === '남성'
                ? 'bg-gray-700 border-white border-[1px] text-white'
                : 'border-gray-300 border-[1px]'
            }`}
            onClick={onClickInput}
          />
          <input
            type="button"
            value={'여성'}
            name="gender"
            className={`grow border-gray-300 border-[1px] rounded-md py-2 cursor-pointer ${
              userForm?.gender && userForm.gender === '여성'
                ? 'bg-gray-700 border-white border-[1px] text-white'
                : 'border-gray-300 border-[1px]'
            }`}
            onClick={onClickInput}
          />
        </div>
        {/** 나이와 지역에 대한 contants 파일 만든 후 map으로 표시 및 input onClick 이벤트 사용 */}
      </label>
      <label htmlFor="age" className="flex flex-col">
        <span className="text-gray-500 my-2">나이</span>
        <div className="flex gap-x-[6px]">
          {ages.map((age) => (
            <input
              id="age"
              type="button"
              value={age}
              placeholder="닉네임"
              className="border-[1px] px-4 py-3 text-xl text-gray-500 rounded-md cursor-pointer"
            />
          ))}
        </div>
      </label>
    </div>
  );
}
