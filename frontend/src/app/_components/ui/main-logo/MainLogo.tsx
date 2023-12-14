import mainLogo from '../../../../../public/yigil-logo-main.png'
import React from 'react'
import Image from 'next/image'

export default function MainLogo() {
  return (
    <>
      <Image src={mainLogo} alt="logo" width={350} />
    </>
  )
}
