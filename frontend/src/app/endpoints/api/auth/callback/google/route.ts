import { NextRequest, NextResponse } from 'next/server';
import { getCallbackUrlBase } from '../kakao/constants';
import { backendLoginRequest } from '../kakao/route';

import { googldRedirectUri } from './constants';

export async function GET(request: NextRequest) {
  const { searchParams } = new URL(request.url);

  const token = searchParams.get('code');
  if (!token) {
    return NextResponse.json({
      message: 'Failed to get Authorization code',
      status: 400,
    });
  }

  const { GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, ENVIRONMENT } = process.env;

  const redirectUri = await googldRedirectUri();

  const userTokenResponse = await userTokenRequest(
    token,
    GOOGLE_CLIENT_ID,
    GOOGLE_CLIENT_SECRET,
    redirectUri,
  );

  if (!userTokenResponse.ok) {
    return NextResponse.json({
      message: 'Failed to get user',
      status: 400,
    });
  }
  const userTokenJson = await userTokenResponse.json();

  const userInfoResponse = await userInfoRequest(userTokenJson.access_token);
  if (!userInfoResponse.ok) {
    return NextResponse.json({
      message: 'Failed to get user info',
      status: 400,
    });
  }
  const userInfoJson = await userInfoResponse.json();

  const backendRequestData = {
    id: userInfoJson.id,
    nickname: userInfoJson.name,
    profile_image_url: userInfoJson.picture,
    email: userInfoJson.email,
    provider: 'google',
    accessToken: userTokenJson.access_token,
  };

  const backendResponse = await backendLoginRequest(backendRequestData);

  const backendJson = await backendResponse.json();

  if (!backendResponse.ok) {
    return NextResponse.json(
      { message: 'Failed to login to backend server' },
      { status: 400 },
    );
  }

  const [key, value] = backendResponse.headers
    .getSetCookie()[0]
    .split('; ')[0]
    .split('=');

  const baseUrl = await getCallbackUrlBase();

  const response = NextResponse.redirect(new URL('/', baseUrl), {
    status: 302,
  });

  response.cookies.set({
    name: key,
    value,
    secure: ENVIRONMENT === 'production',
  });

  return response;
}

function userTokenRequest(
  token: string,
  clientId: string,
  clientSecret: string,
  redirectUri: string,
) {
  const queryParams = `code=${token}&client_id=${clientId}&client_secret=${clientSecret}&redirect_uri=${redirectUri}&grant_type=authorization_code`;
  const userTokenUrl = `https://accounts.google.com/o/oauth2/token?${queryParams}`;

  return fetch(userTokenUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
    },
  });
}

function userInfoRequest(token: string) {
  return fetch('https://www.googleapis.com/oauth2/v2/userinfo', {
    headers: {
      authorization: `Bearer ${token}`,
    },
  });
}
