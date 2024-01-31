import React from "react";
import Layout from "./Layout";
import withAuthProtection from "./snippet/withAuthProtection";

const HomePage: React.FC = () => {
  return (
    <Layout>
      <h1>환영합니다!</h1>
      <p>이곳은 이길어때 어드민 페이지입니다.</p>
    </Layout>
  );
};

export default withAuthProtection(HomePage);
