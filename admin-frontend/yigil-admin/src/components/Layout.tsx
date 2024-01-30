import React from "react";

import { ThemeProvider } from "@/components/theme-provider";
import { Header } from "@/components/snippet/Header";

type LayoutProps = {
  children: React.ReactNode;
};

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <header className="border-b-4 px-20 py-2">
        <Header />
      </header>
      <main>{children}</main>
    </ThemeProvider>
  );
};

export default Layout;
