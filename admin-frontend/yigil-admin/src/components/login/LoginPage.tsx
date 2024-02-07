import React, { useState } from "react";
import Layout from "../Layout";
import { useNavigate } from "react-router-dom";

import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { AlertBox } from "../snippet/AlertBox";
import { SignUpDrawer } from "./SignUpDrawer";

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [new_email, setNewEmail] = useState("");
  const [new_name, setNewName] = useState("");

  const [errorName, setErrorName] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const [isOpen, setIsOpen] = useState(false);
  const [isSignUpSuccess, setIsSignUpSuccess] = useState(false);

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleNewEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewEmail(event.target.value);
  };

  const handleNewNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewName(event.target.value);
  };

  const handleLogin = async () => {
    if (!email.trim()) {
      setErrorName("로그인 실패");
      setErrorMessage("이메일을 입력해주세요");
      setIsOpen(true);
      return; // 함수 종료
    }

    if (!password.trim()) {
      setErrorName("로그인 실패");
      setErrorMessage("비밀번호를 입력해주세요");
      setIsOpen(true);
      return; // 함수 종료
    }
    try {
      const response = await fetch(
        "http://localhost:8081/admin/api/v1/admins/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ email, password }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        setErrorName("로그인 실패");
        setErrorMessage(
          errorData.message || "로그인 중 오류가 발생하였습니다."
        );
        setIsOpen(true);
        setPassword("");
        return;
      }

      const { access_token, refresh_token } = await response.json();
      document.cookie = `accessToken=Bearer ${access_token}; path=/;`;
      document.cookie = `refreshToken=${refresh_token}; path=/;`;

      navigate("/");
    } catch (error) {
      setErrorName("로그인 오류");
      setErrorMessage("로그인 처리 중 오류가 발생했습니다.");
      setIsOpen(true);
      setPassword("");
    }
  };

  const handleSignUp = async () => {
    if (!new_email.trim()) {
      setErrorName("회원가입 요청 실패");
      setErrorMessage("이메일을 입력해주세요");
      setIsOpen(true);
      return; // 함수 종료
    }

    if (!new_name.trim()) {
      setErrorName("회원가입 요청 실패");
      setErrorMessage("사용자 이름을 입력해주세요");
      setIsOpen(true);
      return; // 함수 종료
    }
    try {
      const response = await fetch(
        "http://localhost:8081/admin/api/v1/admins/signup",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ email: new_email, nickname: new_name }),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        setErrorName("회원가입 요청 실패");
        setErrorMessage(
          errorData.message || "회원가입 요청 중 오류가 발생하였습니다."
        );
        setIsOpen(true);
        setNewEmail("");
        setNewName("");
        return;
      }

      setIsSignUpSuccess(true);
    } catch (error) {
      setErrorName("회원가입 요청 실패");
      setErrorMessage("회원가입 요청 중 오류가 발생하였습니다.");
      setIsOpen(true);
      setNewEmail("");
      setNewName("");
    }
  };

  return (
    <Layout>
      <Tabs defaultValue="login" className="w-[400px] my-10 mx-auto">
        <TabsList>
          <TabsTrigger value="login">로그인</TabsTrigger>
          <TabsTrigger value="join">회원가입</TabsTrigger>
        </TabsList>
        <TabsContent value="login">
          <Card>
            <CardHeader>
              <CardTitle>로그인</CardTitle>
              <CardDescription>
                이길어때 어드민 서비스를 이용하기 위해 로그인하세요.
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-2">
              <div className="space-y-1">
                <Label htmlFor="email">이메일</Label>
                <Input
                  id="email"
                  type="email"
                  value={email}
                  onChange={handleEmailChange}
                />
              </div>
              <div className="space-y-1">
                <Label htmlFor="password">비밀번호</Label>
                <Input
                  id="password"
                  type="password"
                  value={password}
                  onChange={handlePasswordChange}
                />
              </div>
            </CardContent>
            <CardFooter>
              <Button onClick={handleLogin}>로그인</Button>
            </CardFooter>
          </Card>
        </TabsContent>
        <TabsContent value="join">
          <Card>
            <CardHeader>
              <CardTitle>회원가입</CardTitle>
              <CardDescription>
                어드민 서비스용 계정이 없으신가요?
                <br />
                가입을 요청하시면 승인 후 메일로 결과를 알려드려요.
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-2">
              <div className="space-y-1">
                <Label htmlFor="new_email">이메일</Label>
                <Input
                  id="new_email"
                  type="email"
                  value={new_email}
                  onChange={handleNewEmailChange}
                />
              </div>
              <div className="space-y-1">
                <Label htmlFor="new_name">사용자명</Label>
                <Input
                  id="new_name"
                  value={new_name}
                  onChange={handleNewNameChange}
                />
              </div>
            </CardContent>
            <CardFooter>
              <Button onClick={handleSignUp}>가입신청</Button>
            </CardFooter>
          </Card>
        </TabsContent>
      </Tabs>
      <AlertBox
        isOpen={isOpen}
        close={() => setIsOpen(false)}
        errorMessage={errorMessage}
        errorName={errorName}
      />
      <SignUpDrawer
        isSignUpSuccess={isSignUpSuccess}
        email={new_email}
        close={() => setIsSignUpSuccess(false)}
      />
    </Layout>
  );
};

export default LoginPage;
