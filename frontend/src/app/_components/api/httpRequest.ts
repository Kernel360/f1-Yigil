const BASE_URL =
  typeof window !== 'undefined'
    ? process.env.NEXT_PUBLIC_BASE_URL
    : process.env.BASE_URL;

const headerInitOption = { 'content-type': 'application/json' };
export const httpRequest =
  (url: string) =>
  (params: string = '') =>
  (method: string = 'GET', body: any = null) =>
  (headers: {} = headerInitOption) =>
  async (errorMsg: string = '요청에 실패했습니다') => {
    try {
      const res = await fetch(`${BASE_URL}/v1/${url}${params}`, {
        method,
        headers,
        ...(body && { body: JSON.stringify(body) }),
      });

      if (res.ok) {
        const result = await res.json();
        return result;
      }
    } catch (error) {
      // error 컴포넌트나 토스트 정의 시 사용할 부분
      console.log(error); // 디버그 용
      console.error(errorMsg);
    }
  };

/**
 *  const spotsRequest = httpRequest('spots');
 *  const getSpot = spotsRequest('/1');
 *  const res = await getSpot()()('요청이 실패했습니다.');
 */
