import type { JSX } from 'react';

export {};

declare global {
  namespace NodeJS {
    export interface ProcessEnv {
      GOOGLE_CLIENT_ID: string;
      GOOGLE_CLIENT_SECRET: string;

      KAKAO_ID: string;
      KAKAO_SECRET: string;

      BASE_URL: string;
      DEV_BASE_URL: string;

      NEXT_PUBLIC_BASE_URL: string;
      NEXT_PUBLIC_DEV_BASE_URL: string;

      PRODUCTION_FRONTEND_URL: string;
      DEV_FRONTEND_URL: string;

      NAVER_MAPS_CLIENT_ID: string;
      MAP_SECRET: string;
      NAVER_DEVELOPERS_ID: string;
      NAVER_DEVELOPERS_SECRET: string;

      ENVIRONMENT: 'production' | 'development' | 'local';
    }
  }
}

type GetEventHandlers<T extends keyof JSX.IntrinsicElements> = Extract<
  keyof JSX.IntrinsicElements[T],
  `on${string}`
>;

export type EventFor<
  TElement extends keyof JSX.IntrinsicElements,
  THandler extends GetEventHandlers<TElement>,
> = JSX.IntrinsicElements[TElement][THandler] extends
  | ((e: infer TEvent) => any)
  | undefined
  ? TEvent
  : never;
