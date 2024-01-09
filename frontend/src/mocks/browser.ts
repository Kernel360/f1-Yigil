import { SetupWorker, setupWorker } from 'msw/browser';
import handlers from './handlers';

// This configures a Service Worker with the given request handlers.
export const worker: SetupWorker = setupWorker(...handlers);
worker.start({
  // 처리되지 않은 요청 보이지 않게 처리
  onUnhandledRequest: 'bypass',
});
