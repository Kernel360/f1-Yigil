import { backendLoginRequest } from '@/app/_components/api/action';
import { NextRequest, NextResponse } from 'next/server';
import {
  NAVER_AUTH_BASE_URL,
  NAVER_BASE_URL,
  naverOAuthEndPoint,
  naverRedirectUri,
} from './constants';
import { getCallbackUrlBase } from '../kakao/constants';

export async function GET(request: NextRequest) {
  const baseUrl = await getCallbackUrlBase();
  const { searchParams } = new URL(request.url);

  const code = searchParams.get('code');

  if (!code) {
    return NextResponse.redirect(
      new URL('/login?status=failed&reason=code', baseUrl),
    );
  }

  const { NAVER_SEARCH_ID, NAVER_SEARCH_SECRET, ENVIRONMENT } = process.env;

  const redirectUri = await naverRedirectUri();

  const userTokenResponse = await userTokenRequest(
    NAVER_SEARCH_ID,
    redirectUri,
    code,
    NAVER_SEARCH_SECRET,
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
  const { response: userInfoJson } = await userInfoResponse.json();

  const backendRequestData = {
    id: userInfoJson.id,
    nickname: userInfoJson.nickname,
    profile_image_url: userInfoJson.profile_image,
    email: userInfoJson.email,
    provider: 'naver',
    accessToken: userTokenJson.access_token,
  };

  const backendResponse = await backendLoginRequest(backendRequestData);

  if (!backendResponse.ok) {
    return NextResponse.redirect(
      new URL('/login?status=failed&reason=userinfo', baseUrl),
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
  clientId: string,
  redirectUri: string,
  code: string,
  secret: string,
) {
  const requestData = new Map<string, string>();
  requestData.set('grant_type', 'authorization_code');
  requestData.set('client_id', clientId);
  requestData.set('redirect_uri', redirectUri);
  requestData.set('code', code);
  requestData.set('client_secret', secret);
  const queryString = Array.from(requestData.entries())
    .map(([key, value]) => `${key}=${value}`)
    .join('&');

  const userTokenUrl = `${NAVER_AUTH_BASE_URL}/token?${queryString}`;

  return fetch(userTokenUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
    },
    cache: 'no-store',
  });
}

function userInfoRequest(accessToken: string) {
  const userInfoUrl = `${NAVER_BASE_URL}`;

  return fetch(userInfoUrl, {
    headers: {
      'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      Authorization: `Bearer ${accessToken}`,
    },
    cache: 'no-store',
  });
}
