'use client'

import React from 'react'
import KakaoBtn from '@/app/_components/ui/button/Kakao'
import MainLogo from '@/app/_components/ui/main-logo/MainLogo'
import { useSession } from 'next-auth/react'
// import GoogleBtn from '@/app/_components/ui/button/Google'

export default function LoginPage() {
  return (
    <div className="w-full h-screen bg-main flex flex-col justify-center items-center">
      <MainLogo />
      <div className="text-xl text-[white]">서비스를 사용하시려면 로그인 해주세요</div>
      <div className="mt-8 flex flex-col items-center justify-center gap-4">
        <KakaoBtn />
        {/* <GoogleBtn /> */}
      </div>
    </div>
  )
}
