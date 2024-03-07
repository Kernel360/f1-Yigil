'use server';

export async function getBaseUrl() {
  const { BASE_URL, DEV_BASE_URL, NODE_ENV } = process.env;

  return NODE_ENV === 'production' ? BASE_URL : DEV_BASE_URL;
}
