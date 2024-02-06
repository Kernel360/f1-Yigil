import NextAuth from 'next-auth';
import GoogleProvider from 'next-auth/providers/google';
import KaKaoProvider from 'next-auth/providers/kakao';

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
      const res = await fetch(`${process.env.BASE_URL}/v1/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${account?.access_token}`,
        },
        body: postUser,
      });
      if (res.ok) return true;
      else return '/';
    },

    // async jwt({ token, user }) {
    //   return { ...token, ...user }
    // },

    // async session({ session, token }) {
    //   session.user = token as any
    //   return session
    // },
  },

  pages: {
    signIn: '/login',
    error: '/login_error',
  },
});

export { handler as GET, handler as POST };
