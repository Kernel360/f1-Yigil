import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
function withAuthProtection<T extends React.ComponentPropsWithoutRef<any>>(
  WrappedComponent: React.ComponentType<T>
) {
  return function ProtectedComponent(props: T) {
    const navigate = useNavigate();

    useEffect(() => {
      const accessToken = getCookie("accessToken");
      if (!accessToken) {
        navigate("/admin/login");
      }
    }, [navigate]);

    return <WrappedComponent {...(props as JSX.IntrinsicAttributes & T)} />;
  };
}

export default withAuthProtection;

function getCookie(name: string): string | undefined {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop()?.split(";").shift();
}
