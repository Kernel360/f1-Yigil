export const getCoords = async (address: string) => {
  const res = await fetch(
    `https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=${address}`,
    {
      method: 'GET',
      headers: {
        'X-NCP-APIGW-API-KEY-ID': process.env.NEXT_PUBLIC_NAVER_MAPS_CLIENT_ID,
        'X-NCP-APIGW-API-KEY': process.env.MAP_SECRET,
      } as Record<string, string>,
    },
  );
  const result = await res.json();
  return { lat: result.addresses[0].y, lng: result.addresses[0].x };
};
