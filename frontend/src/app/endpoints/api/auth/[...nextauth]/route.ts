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
    async signIn({ user }) {
      console.log(user);

      /**
       * 백엔드와 api 통신
       * 회원가입 해야 하는
       * if(user.email)
       */
      // 로그인 된 다면 true 안되면 redirect end
      return true;
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
