export {};

declare global {
  namespace NodeJS {
    export interface ProcessEnv {
      NEXTAUTH_SECRET: string;

      GOOGLE_CLIENT_ID: string;
      GOOGLE_CLIENT_SECRET: string;

      KAKAO_ID: string;
      KAKAO_SECRET: string;
    }
  }
}
