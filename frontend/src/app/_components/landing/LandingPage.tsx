'use client'

import Image from 'next/image'
import React from 'react'
import MainLogo from '../ui/main-logo/MainLogo'
export default function LandingPage() {
  return (
    <div className="bg-main w-screen h-screen flex justify-center items-center">
      <MainLogo />
    </div>
  )
}
