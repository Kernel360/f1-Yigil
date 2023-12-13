import MainLogo from '@/app/_components/ui/main-logo/MainLogo'
import Link from 'next/link'
import React from 'react'

export default function LoginErrorPage() {
  return (
    <div className="w-full h-full bg-main flex flex-col justify-center items-center font-bold ">
      <MainLogo />
      <Link href="/login" className="text-[white] text-2xl mt-[40px]">
        다시 로그인 하기
      </Link>
    </div>
  )
}
