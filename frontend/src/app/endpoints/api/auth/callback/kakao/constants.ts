export const KAKAO_BASE_URL = 'https://kapi.kakao.com';
export const KAKAO_AUTH_BASE_URL = 'https://kauth.kakao.com';

export function getCallbackUrlBase() {
  const { ENVIRONMENT, BASE_URL } = process.env;

  return ENVIRONMENT === 'production' ? BASE_URL : 'http://localhost:3000';
}

export function kakaoRedrectUri() {
  return `${getCallbackUrlBase()}/endpoints/api/auth/callback/kakao`;
}

export function kakaoOAuthEndpoint(clientId: string) {
  const endpoint = `${KAKAO_AUTH_BASE_URL}/oauth/authorize`;
  const queryParams = `client_id=${clientId}&redirect_uri=${kakaoRedrectUri()}&response_type=code`;

  return `${endpoint}?${queryParams}`;
}
