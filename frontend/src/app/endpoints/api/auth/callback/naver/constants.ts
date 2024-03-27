import { getCallbackUrlBase } from '../kakao/constants';

export const NAVER_BASE_URL = 'https://openapi.naver.com/v1/nid/me';
export const NAVER_AUTH_BASE_URL = 'https://nid.naver.com/oauth2.0';

export async function naverRedirectUri() {
  const callbackUrlBase = await getCallbackUrlBase();
  return `${callbackUrlBase}/endpoints/api/auth/callback/naver`;
}

export async function naverOAuthEndPoint(clientId: string) {
  const endPoint = `${NAVER_AUTH_BASE_URL}/authorize`;
  const redirectUri = await naverRedirectUri();
  const queryParams = `client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code`;

  return `${endPoint}?${queryParams}`;
}
