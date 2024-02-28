'use server';

import { requestWithCookie } from '../../api/httpRequest';

export const authenticateUser = async () => {
  return requestWithCookie('members')()()()();
};
