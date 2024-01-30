import React, { useState, useEffect } from "react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { EnvelopeOpenIcon } from "@radix-ui/react-icons";
import { Link } from "react-router-dom";

interface UserInfoProps {
  username: string;
  profileUrl: string;
}

const UserInfo: React.FC = () => {
  const [user, setUser] = useState<UserInfoProps | null>(null);

  useEffect(() => {
    const accessToken = getCookie("accessToken");

    if (accessToken) {
      fetch("https://yigil.co.kr/admin/api/v1/login", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
        .then((response) => response.json())
        .then((data) => setUser(data))
        .catch((error) => console.error("Error:", error));
    }
  }, []);

  return (
    <div>
      {user ? (
        <Avatar>
          <AvatarImage src={user.profileUrl} alt={`@${user.username}`} />
          <AvatarFallback>{user.username[0]}</AvatarFallback>
        </Avatar>
      ) : (
        <Link to="/login">
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
