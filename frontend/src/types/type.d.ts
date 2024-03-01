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
      NEXT_PUBLIC_BASE_URL: string;

      NEXT_PUBLIC_NAVER_MAPS_CLIENT_ID: string;

      NAVER_MAPS_CLIENT_ID: string;
      NAVER_SEARCH_ID: string;
      NAVER_SEARCH_SECRET: string;
      MAP_SECRET: string;

      DEV_BASE_URL: string;
      ENVIRONMENT: string;

      PRODUCTION_FRONTEND_URL: string;
      DEV_FRONTEND_URL: string;
    }
  }
}

declare module '*.svg' {
  import React = require('react');
  export const ReactComponent: React.FunctionComponent<
    React.SVGProps<SVGSVGElement>
  >;
  const src: string;
  export default src;
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
