'use client'

import React, { useEffect } from 'react'
import Image from 'next/image'
import mainLogo from '/public/2gil-logo-main.png'
import { useSession } from 'next-auth/react'

import { redirect } from 'next/navigation'
// import KakaoBtn from '@/app/_components/ui/button/Kakao'
// import GoogleBtn from '@/app/_components/ui/button/Google'

export default function LoginPage() {
  const { data: session } = useSession()
  useEffect(() => {
    console.log(session)
    // if (session) redirect('/home')
  }, [])
  return (
    <div className="w-full h-screen bg-main flex flex-col justify-center items-center">
      {/* <Image src={mainLogo} alt="logo" width={400} />
      <div className="text-2xl text-[white] font-bold">서비스를 사용하시려면 로그인 해주세요</div>
      <div className="mt-8 flex flex-col items-center justify-center gap-4">
        <KakaoBtn />
        <GoogleBtn />
      </div> */}
    </div>
  )
}
