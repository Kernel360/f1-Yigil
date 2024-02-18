import React from "react";

import Layout from "./Layout";

import { RocketIcon } from "@radix-ui/react-icons";

import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";

const NotFound: React.FC = () => {
  return (
    <Layout>
      <Alert>
        <RocketIcon className="h-4 w-4" />
        <AlertTitle>Heads up!</AlertTitle>
        <AlertDescription>
          열심히 개발중인 메뉴입니다! 다른 메뉴를 확인해주세요.
        </AlertDescription>
      </Alert>
    </Layout>
  );
};

export default NotFound;
