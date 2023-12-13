'use client'

import Image from 'next/image'
import React from 'react'
import mainLogo from '../../../../public/2gil-logo-main.png'
export default function LandingPage() {
  return (
    <div className="bg-main w-screen h-screen flex justify-center items-center">
      <Image src={mainLogo} alt="logo" width={400} />
    </div>
  )
}
