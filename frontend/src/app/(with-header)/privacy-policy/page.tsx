import Image from 'next/image';
import Link from 'next/link';
import React from 'react';
import GoogleLogo from '/public/loginBtns/google_login.svg';

export default function PrivacyPolicyPage() {
  return (
    <div className="w-full mt-10 px-3">
      <Link
        href="https://docs.google.com/forms/d/e/1FAIpQLSfsbhvjAjiY_KSTTUrWNcGB8A7gXshwRW0Or7e_vvAbpGBVgg/viewform"
        className="flex flex-col items-center border-2"
      >
        <GoogleLogo className="w-10 h-10" />
        <p>마음의 소리함</p>
      </Link>
      <div className="mt-10 text-sm">
        <div className="flex flex-col gap-y-4 break-keep">
          <p className="text-lg text-main font-bold text-center">
            [yigil.co.kr] 개인정보처리방침
          </p>
          <p>
            1. 수집하는 개인정보 항목 - 사용자의 신분을 확인하기 위해 수집하는
            정보: [ID, 이름, 이메일 주소] <br /> - 서비스 이용 과정에서 생성되는
            정보: [서비스 이용 기록, 접속 로그] <br /> - Google API로부터
            가져오는 정보: [ID, 이름, 이메일 주소, 프로필 사진]
          </p>
          <p>
            2. 개인정보의 수집 및 이용목적 - 웹 서비스 제공 및 유지보수 - 이용자
            식별 및 본인 확인 - 개인맞춤 서비스 제공 - 서비스 이용 통계 및 분석
          </p>
          <p>
            3. 개인정보의 보유 및 이용기간 - 개인정보는 수집 목적이 달성된 후
            즉시 파기되거나 분리 저장되어 보관되며, 이용기간은 서비스 제공
            목적에 따라 다를 수 있음.
          </p>
          <p>
            4. 개인정보의 제3자 제공 - 해당 웹 서비스는 사용자 동의 없이
            개인정보를 외부에 제공하지 않으며, 예외적인 경우에는 법령에 따라
            제공될 수 있음.
          </p>
          <p>
            5. 개인정보의 파기 - 개인정보는 수집 및 이용 목적이 달성된 후 즉시
            파기되거나 분리 저장되어 보관되며, 파기 기준은 관련 법령에 따라
            이루어짐.
          </p>
          <p>
            6. 개인정보 보호책임자 - 개인정보 관리에 대한 문의사항 및 민원 처리
            담당자 정보 제공
          </p>
        </div>
      </div>
    </div>
  );
}
