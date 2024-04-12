import { backendLoginRequest } from '@/app/_components/api/action';
import { NextRequest, NextResponse } from 'next/server';
import { getCallbackUrlBase } from '../kakao/constants';
import { googldRedirectUri } from './constants';

export async function GET(request: NextRequest) {
  const baseUrl = await getCallbackUrlBase();
  const { searchParams } = new URL(request.url);

  const token = searchParams.get('code');
  if (!token)
    return NextResponse.redirect(
      new URL('/login?status=failed&reason=code', baseUrl),
    );

  const { GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, ENVIRONMENT } = process.env;

  const redirectUri = await googldRedirectUri();

  const userTokenResponse = await userTokenRequest(
    token,
    GOOGLE_CLIENT_ID,
    GOOGLE_CLIENT_SECRET,
    redirectUri,
  );

  if (!userTokenResponse.ok) {
    return NextResponse.redirect(
      new URL('/login?status=failed&reason=userinfo', baseUrl),
    );
  }
  const userTokenJson = await userTokenResponse.json();

  const userInfoResponse = await userInfoRequest(userTokenJson.access_token);
  if (!userInfoResponse.ok) {
    return NextResponse.redirect(
      new URL('/login?status=failed&reason=userinfo', baseUrl),
    );
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
    return NextResponse.redirect(
      new URL('/login?status=failed&reason=server', baseUrl),
    );
  }

  const [key, value] = backendResponse.headers
    .getSetCookie()[0]
    .split('; ')[0]
    .split('=');

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
