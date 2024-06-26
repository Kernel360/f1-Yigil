import { backendLoginRequest } from '@/app/_components/api/action';
import { NextRequest, NextResponse } from 'next/server';
import {
  KAKAO_AUTH_BASE_URL,
  KAKAO_BASE_URL,
  kakaoRedrectUri,
  getCallbackUrlBase,
} from './constants';

export async function GET(request: NextRequest) {
  const baseUrl = await getCallbackUrlBase();
  const { searchParams } = new URL(request.url);

  const code = searchParams.get('code');

  if (!code)
    return NextResponse.redirect(
      new URL('/login?status=failed&reason=code', baseUrl),
    );

  const { KAKAO_ID, KAKAO_SECRET, ENVIRONMENT } = process.env;

  const redirectUri = await kakaoRedrectUri();

  const userTokenResponse = await userTokenRequest(
    KAKAO_ID,
    redirectUri,
    code,
    KAKAO_SECRET,
  );

  // 토큰 만료 시 관리법 필요
  const userTokenJson = await userTokenResponse.json();

  if (!userTokenResponse.ok) {
    console.log(userTokenJson);

    return NextResponse.redirect(
      new URL('/login?status=failed&reason=userinfo', baseUrl),
    );
  }

  const accessToken: string = userTokenJson.access_token;

  // Schema 작성
  const userInfoResponse = await userInfoRequest(accessToken);

  const userInfoJson = await userInfoResponse.json();

  if (!userInfoResponse.ok) {
    console.log(userInfoJson);

    return NextResponse.redirect(
      new URL('/login?status=failed&reason=userinfo', baseUrl),
    );
  }

  // Schema 작성
  const id: string = userInfoJson.id;
  const nickname: string = userInfoJson.kakao_account.profile.nickname;
  const profile_image_url: string =
    userInfoJson.kakao_account.profile.profile_image_url;
  const email: string = userInfoJson.kakao_account.email;

  const backendRequestData = {
    id,
    nickname,
    profile_image_url,
    email,
    provider: 'kakao',
    accessToken,
  };

  const backendResponse = await backendLoginRequest(backendRequestData);

  const backendJson = await backendResponse.json();

  if (!backendResponse.ok) {
    console.log(backendJson);
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

  const userTokenUrl = `${KAKAO_AUTH_BASE_URL}/oauth/token?${queryString}`;

  return fetch(userTokenUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
    },
    cache: 'no-store',
  });
}

function userInfoRequest(accessToken: string) {
  const userInfoUrl = `${KAKAO_BASE_URL}/v2/user/me`;

  return fetch(userInfoUrl, {
    headers: {
      'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      Authorization: `Bearer ${accessToken}`,
    },
    cache: 'no-store',
  });
}
