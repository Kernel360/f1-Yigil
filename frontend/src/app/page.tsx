'use client'

import { useEffect } from 'react'
import LandingPage from './_components/landing/LandingPage'

import { useRouter } from 'next/navigation'

export default function Home() {
  const router = useRouter()
  // 세션 확인 후 로그인으로 보낼지 메인페이지로 보낼지.
  useEffect(() => {
    const timer = setTimeout(() => {
      router.push('/login')
    }, 1500)
    return () => clearTimeout(timer)
  }, [])
  return <LandingPage />
}
