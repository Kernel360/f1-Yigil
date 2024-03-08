export const KAKAO_BASE_URL = 'https://kapi.kakao.com';
export const KAKAO_AUTH_BASE_URL = 'https://kauth.kakao.com';

export async function getCallbackUrlBase() {
  const { ENVIRONMENT, PRODUCTION_FRONTEND_URL, DEV_FRONTEND_URL } =
    process.env;

  return ENVIRONMENT === 'production'
    ? PRODUCTION_FRONTEND_URL
    : ENVIRONMENT === 'development'
    ? DEV_FRONTEND_URL
    : 'http://localhost:3000';
}

export async function kakaoRedrectUri() {
  const callbackUrlBase = await getCallbackUrlBase();

  return `${callbackUrlBase}/endpoints/api/auth/callback/kakao`;
}

export async function kakaoOAuthEndpoint(clientId: string) {
  const endpoint = `${KAKAO_AUTH_BASE_URL}/oauth/authorize`;
  const redirectUri = await kakaoRedrectUri();
  const queryParams = `client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code`;

  return `${endpoint}?${queryParams}`;
}
