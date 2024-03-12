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

      PRODUCTION_FRONTEND_URL: string;
      DEV_FRONTEND_URL: string;

      NAVER_MAPS_CLIENT_ID: string;
      MAP_SECRET: string;
      NAVER_SEARCH_ID: string;
      NAVER_SEARCH_SECRET: string;

      ENVIRONMENT: 'production' | 'development' | 'local';
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
