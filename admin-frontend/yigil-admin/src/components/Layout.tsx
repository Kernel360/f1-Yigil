import React from "react";

import { ThemeProvider } from "@/components/theme-provider";
import { Header } from "@/components/snippet/Header";

type LayoutProps = {
  children: React.ReactNode;
};

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <header>
        <Header />
      </header>
      <main>{children}</main>
      <footer>여기는 푸터입니다</footer>
    </ThemeProvider>
  );
};

export default Layout;
