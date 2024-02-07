import { Button } from "@/components/ui/button";
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
} from "@/components/ui/drawer";

export function SignUpDrawer({
  email,
  isSignUpSuccess,
  close,
}: {
  email: string;
  isSignUpSuccess: boolean;
  close: () => void;
}) {
  return (
    <Drawer open={isSignUpSuccess}>
      <DrawerContent>
        <div className="mx-auto w-full max-w-sm">
          <DrawerHeader>
            <DrawerTitle>회원가입 요청 완료</DrawerTitle>
            <DrawerDescription>
              가입 승인 결과는 메일로 알려드려요. 메일 주소를 잘못 작성하였다면
              관리자에게 문의하세요.
            </DrawerDescription>
          </DrawerHeader>
          <div className="flex items-center justify-center space-x-2">
            이메일 주소: {email}
          </div>
          <DrawerFooter>
            <DrawerClose asChild>
              <Button variant="outline" onClick={close}>
                닫기
              </Button>
            </DrawerClose>
          </DrawerFooter>
        </div>
      </DrawerContent>
    </Drawer>
  );
}
