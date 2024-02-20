import { setupServer } from 'msw/node';
import handlers from './mocks/handlers';

import { afterAll, afterEach, beforeAll } from 'vitest';
const server = setupServer(...handlers);
// beforeAll()에서 msw 서버 시작
beforeAll(() => server.listen());

// afterEach()에서 msw 서버 리셋
afterEach(() => server.resetHandlers());

// afterAll()에서 msw 서버 종료
afterAll(() => server.close());
