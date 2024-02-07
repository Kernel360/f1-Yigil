export const getArea = async (title: string) => {
  const mapResult = await fetch(
    `https://openapi.naver.com/v1/search/local.json?query=${title}&display=10&sort=random`,
    {
      method: 'GET',
      headers: {
        'X-Naver-Client-Id': process.env.NAVER_SEARCH_ID,
        'X-Naver-Client-Secret': process.env.NAVER_SEARCH_SECRET,
      } as Record<string, string>,
    },
  );
  const mapRes = await mapResult.json();
  return mapRes.items;
};
