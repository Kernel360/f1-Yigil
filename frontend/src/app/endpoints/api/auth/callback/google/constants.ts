import { getCallbackUrlBase } from '../kakao/constants';

export const GOOGLE_AUTH_URL = 'https://accounts.google.com/o/oauth2/v2/auth';
export const GOOGLE_AUTH_SCOPE = 'openid email profile';
export async function googldRedirectUri() {
  const callbackUrl = await getCallbackUrlBase();
  return `${callbackUrl}/endpoints/api/auth/callback/google`;
}

export async function googleOAuthEndPoint(clientId: string) {
  const endPoint = `${GOOGLE_AUTH_URL}`;
  const redirectUri = await googldRedirectUri();
  const queryParams = `client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code&scope=${GOOGLE_AUTH_SCOPE}`;

  return `${endPoint}?${queryParams}`;
}
