import NextAuth from 'next-auth';
import GoogleProvider from 'next-auth/providers/google';
import KaKaoProvider from 'next-auth/providers/kakao';
import { cookies } from 'next/headers';

const BASE_URL =
  process.env.ENVIRONMENT === 'production'
    ? process.env.BASE_URL
    : process.env.DEV_BASE_URL;

const handler = NextAuth({
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET,
    }),
    KaKaoProvider({
      clientId: process.env.KAKAO_ID,
      clientSecret: process.env.KAKAO_SECRET,
    }),
  ],
  secret: process.env.NEXTAUTH_SECRET,
  callbacks: {
    async signIn({ user, account }) {
      const postUser = JSON.stringify({
        ...user,
        nickname: user.name,
        provider: account?.provider,
      });
      const res = await fetch(`${BASE_URL}/v1/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${account?.access_token}`,
        },
        body: postUser,
      });

      if (res.ok) {
        const [key, value] = res.headers
          .getSetCookie()[0]
          .split('; ')[0]
          .split('=');
        cookies().set({
          name: key,
          value,
          httpOnly: true,
          sameSite: 'strict',
          secure: true,
        });
        return true;
      } else return '/';
    },
  },
  events: {
    signOut: async () => {
      const res = await fetch(`${BASE_URL}/v1/logout`, {
        method: 'GET',
        headers: {
          Cookie: `SESSION=${cookies().get('SESSION')?.value}`,
        },
      });

      if (res.ok) {
        cookies().delete('SESSION');
      } else {
        console.log(`${res.status} ${res.statusText}`);
      }
    },
  },
  pages: {
    signIn: '/login',
    error: '/login_error',
  },
});

export { handler as GET, handler as POST };
