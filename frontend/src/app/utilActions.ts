'use server';

export async function getBaseUrl() {
  const { BASE_URL, DEV_BASE_URL, ENVIRONMENT } = process.env;

  return ENVIRONMENT === 'production' ? BASE_URL : DEV_BASE_URL;
}
