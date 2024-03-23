import { myInfoSchema } from '@/types/response';
import { authenticateUser } from '@/app/_components/mypage/hooks/authenticateUser';
import LoggedOutHome from './LoggedOutHome';
import LoggedInHome from './LoggedInHome';

export default async function HomePage() {
  const memberJson = await authenticateUser();
  const memberInfo = myInfoSchema.safeParse(memberJson);

  const isLoggedIn = memberInfo.success;

  if (!isLoggedIn) {
    return <LoggedOutHome />;
  }

  return <LoggedInHome />;
}
