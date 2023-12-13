'use client'
import React from 'react'
import { signOut } from 'next-auth/react'
import { redirect, useRouter } from 'next/navigation'
export default function Home() {
  const router = useRouter()
  const onClickSignOutBtn = () => {
    signOut()
    // fetch(
    //   `https://kauth.kakao.com/oauth/logout?client_id=${process.env.KAKAO_ID}&logout_redirect_uri=http://localhost:3000/api/auth/callback/kakao`
    // )
    //   .then((res) => res.json())
    //   .then((result) => console.log(result))
    // router.push(
    //   `https://kauth.kakao.com/oauth/logout?client_id=${
    //     process.env.KAKAO_ID || ''
    //   }&logout_redirect_uri=`
    // )
    router.push('/login')
  }

  return (
    <div className="w-full h-screen">
      <button onClick={onClickSignOutBtn}>logout</button>
    </div>
  )
}
