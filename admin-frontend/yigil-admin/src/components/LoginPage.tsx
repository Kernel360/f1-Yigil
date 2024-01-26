import React from "react";
import Layout from "./Layout";

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

const LoginPage: React.FC = () => {
  return (
    <Layout>
      <Tabs defaultValue="login" className="w-[400px]">
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
                <Input id="email" type="email" />
              </div>
              <div className="space-y-1">
                <Label htmlFor="password">비밀번호</Label>
                <Input id="username" type="password" />
              </div>
            </CardContent>
            <CardFooter>
              <Button>로그인</Button>
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
                <Input id="new_email" type="email" />
              </div>
              <div className="space-y-1">
                <Label htmlFor="new_name">사용자명</Label>
                <Input id="new_name" />
              </div>
              <div className="space-y-1">
                <Label htmlFor="new_password">비밀번호</Label>
                <Input id="new_password" type="password" />
              </div>
            </CardContent>
            <CardFooter>
              <Button>가입신청</Button>
            </CardFooter>
          </Card>
        </TabsContent>
      </Tabs>
    </Layout>
  );
};

export default LoginPage;
