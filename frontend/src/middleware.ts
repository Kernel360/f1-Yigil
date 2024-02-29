import { withAuth } from 'next-auth/middleware';
import { NextRequest, NextResponse } from 'next/server';

export default function middleware(request: NextRequest) {
  const { ENVIRONMENT } = process.env;

  const response = NextResponse.next();

  if (request.cookies.has('SESSION')) {
    console.log('THIS REQUEST HAS SESSION!!!');

    const session = request.cookies.get('SESSION');

    if (!session) {
      return response;
    }

    response.cookies.set({
      name: session.name,
      value: session.value,
      httpOnly: true,
      secure: ENVIRONMENT === 'production',
      sameSite: 'strict',
    });

    return response;
  }

  return response;
}

// export default withAuth({
//   pages: {
//     signIn: '/login',
//   },
// });

// export const config = { matcher: ['/add/:path*', '/mypage/:path*'] };
