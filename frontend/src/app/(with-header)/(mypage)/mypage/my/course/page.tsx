import React from 'react';
//
const url =
  process.env.NODE_ENV === 'production'
    ? process.env.BASE_URL
    : 'http://localhost:8080/api/v1';
export default async function MyPageMyCourse() {
  const res = await fetch(`${url}/member/spot`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  console.log(res);
  return <div>MyPageMyCourse</div>;
}
