import { EventFor } from '@/types/type';
import React, { Dispatch, SetStateAction, useState } from 'react';
import { regions } from '../regions/constants';
import { ages } from './contants';
import { TModifyUser } from './ModifyUser';

export interface PropsType {
  userForm: TModifyUser | undefined;
  setUserForm: Dispatch<SetStateAction<TModifyUser | undefined>>;
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

  const onClickArea = (e: EventFor<'input', 'onClick'>) => {
    if (userForm?.area?.includes(e.currentTarget.value)) {
      const popArea = userForm?.area.filter((i) => i !== e.currentTarget.value);
      setUserForm({ ...userForm, area: popArea });
    } else {
      const addedArea = userForm?.area && [
        ...userForm.area,
        e.currentTarget.value,
      ];
      setUserForm({
        ...userForm,
        area: addedArea,
      });
    }
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
          className="border-[1px] px-4 py-2 text-2xl rounded-md"
          onChange={onChangeInput}
        />
      </label>

      <div className="flex flex-col">
        <span className="text-gray-500 my-2">성별</span>
        <div className="flex items-center text-gray-300 text-2xl text-center gap-x-2">
          <input
            type="button"
            value={'남성'}
            name="gender"
            className={`grow  rounded-md py-2 cursor-pointer ${
              userForm?.gender === '남성'
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
              userForm?.gender === '여성'
                ? 'bg-gray-700 border-white border-[1px] text-white'
                : 'border-gray-300 border-[1px]'
            }`}
            onClick={onClickInput}
          />
        </div>
      </div>
      <div className="flex flex-col">
        <span className="text-gray-500 my-2">나이</span>
        <div className="flex gap-x-[6px]">
          {ages.map((age) => (
            <input
              key={age}
              type="button"
              value={age}
              name="age"
              className={`border-[1px] px-4 py-3 text-xl text-gray-500 rounded-md cursor-pointer
              ${
                userForm?.age === age
                  ? 'bg-gray-700 border-white border-[1px] text-white'
                  : 'border-gray-300 border-[1px]'
              }`}
              onClick={onClickInput}
            />
          ))}
        </div>
      </div>
      <div className="flex flex-col">
        <span className="text-gray-500 my-2">관심 지역</span>
        <div className="grid grid-cols-5 gap-x-[6px] gap-y-2">
          {regions.map(({ label }) => (
            <input
              key={label}
              type="button"
              value={label}
              className={`border-[1px] px-4 py-3 text-xl text-gray-500 rounded-md cursor-pointer
              ${
                userForm?.area?.includes(label)
                  ? 'bg-gray-700 border-white border-[1px] text-white'
                  : 'border-gray-300 border-[1px]'
              }`}
              onClick={onClickArea}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
