import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { EnvelopeOpenIcon } from "@radix-ui/react-icons";
import { Link } from "react-router-dom";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";

interface UserInfoProps {
  username: string;
  profile_url: string;
}

const UserInfo: React.FC = () => {
  const [user, setUser] = useState<UserInfoProps | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const accessToken = getCookie("accessToken");

    if (accessToken) {
      fetch("https://yigil.co.kr/admin/api/v1/admins/info", {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
        .then((response) => response.json())
        .then((data) => setUser(data))
        .catch((error) => console.error("Error:", error));
    }
  }, []);

  const deleteCookie = (name: string) => {
    document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
  };

  const handleLogout = () => {
    deleteCookie("accessToken");
    deleteCookie("refreshToken");

    navigate("/admin/login");
  };

  return (
    <div>
      {user ? (
        <Popover>
          <PopoverTrigger asChild>
            <Avatar>
              <AvatarImage src={user.profile_url} alt={`@${user.username}`} />
              <AvatarFallback>{user.username[0]}</AvatarFallback>
            </Avatar>
          </PopoverTrigger>
          <PopoverContent className="w-80">
            <div className="grid gap-4">
              <div className="space-y-2">
                <h4 className="fond-medium leading-none">{user.username}</h4>
                <p className="text-sm text-muted-foreground">
                  관리자님, 환영합니다!
                </p>
              </div>
              <div className="grid gap-2">
                <Button onClick={handleLogout}>로그아웃</Button>
              </div>
            </div>
          </PopoverContent>
        </Popover>
      ) : (
        <Link to="/admin/login">
          <Button>
            <EnvelopeOpenIcon className="mr-2 h-4 w-4" /> Login with Email
          </Button>
        </Link>
      )}
    </div>
  );
};

export default UserInfo;

function getCookie(name: string): string | undefined {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop()?.split(";").shift();
}
