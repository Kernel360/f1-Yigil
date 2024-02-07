import React from "react";
import { Link } from "react-router-dom";

import { cn } from "@/lib/utils";

import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuList,
  NavigationMenuLink,
  NavigationMenuTrigger,
} from "@/components/ui/navigation-menu";
import { ModeToggle } from "@/components/snippet/mode-toggle";
import UserInfo from "@/components/snippet/UserInfo";

import ReactComponent from "@/assets/logo.svg?react";

export function Header() {
  return (
    <NavigationMenu>
      <NavigationMenuList className=" flex justify-between">
        <NavigationMenuItem>
          <Link to="/admin">
            <ReactComponent className="w-[100px] h-[58px] h-4 stroke-black stroke-[2px] fill-slate-100" />
          </Link>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger>서비스 관리</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[400px] lg:w-[500px] lg:grid-cols-[.75fr_1fr]">
              <li className="row-span-3">
                <NavigationMenuLink asChild>
                  <a
                    className="flex h-full w-full select-none flex-col justify-end rounded-md bg-gradient-to-b from-muted/50 to-muted p-6 no-underline outline-none focus:shadow-md"
                    href="https://yigil.co.kr/"
                  >
                    <div className="mb-2 mt-4 text-lg font-medium">
                      yigil-log
                    </div>
                    <p className="text-sm leading-tight text-muted-forground">
                      내가 갔던 나만의 거리를 저장하고 공유해보세요.
                    </p>
                  </a>
                </NavigationMenuLink>
              </li>
              <ListItem href="/admin/notice" title="공지사항 관리">
                사용자를 위한 공지사항을 작성하세요.
              </ListItem>
              <ListItem href="/admin/event" title="이벤트 관리">
                이벤트를 생성하고 관리하세요.
              </ListItem>
              <ListItem href="/admin/coupon" title="쿠폰 관리">
                쿠폰을 생성하고 지급하세요.
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger>사용자 관리</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[200px] lg:w-[800px] lg:grid-cols-[.75fr_1fr]">
              <ListItem href="/admin/member" title="사용자 관리">
                서비스를 사용하는 사용자를 관리하세요.
              </ListItem>
              <ListItem href="/admin/admin" title="어드민 가입 요청 관리">
                관리자 사용자의 가입 요청을 관리하세요.
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger>통계 관리</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[200px] lg:w-[800px] lg:grid-cols-[.75fr_1fr]">
              <ListItem href="/admin/place" title="지역/장소 통계">
                서비스에 지역과 장소에 대한 통계를 확인해보세요.
              </ListItem>
              <ListItem href="/admin/like" title="좋아요 통계">
                사용자 좋아요에 따른 통계를 확인해보세요.
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger>게시글 관리</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[200px] lg:w-[800px] lg:grid-cols-[.75fr_1fr]">
              <ListItem href="/admin/post" title="게시글 관리">
                서비스에 작성된 게시글들을 확인하세요.
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <NavigationMenuTrigger>신고 관리</NavigationMenuTrigger>
          <NavigationMenuContent>
            <ul className="grid gap-3 p-4 md:w-[200px] lg:w-[800px] lg:grid-cols-[.75fr_1fr]">
              <ListItem href="/admin/report" title="신고 관리">
                사용자의 불편사항을 확인하고 개선해보세요.
              </ListItem>
            </ul>
          </NavigationMenuContent>
        </NavigationMenuItem>
        <NavigationMenuItem>
          <UserInfo />
        </NavigationMenuItem>
        <NavigationMenuItem>
          <ModeToggle />
        </NavigationMenuItem>
      </NavigationMenuList>
    </NavigationMenu>
  );
}

interface ListItemProps extends React.ComponentPropsWithoutRef<"a"> {
  title: string;
  href: string;
}

const ListItem = React.forwardRef<HTMLAnchorElement, ListItemProps>(
  ({ className, title, children, href, ...props }, ref) => {
    return (
      <li>
        <NavigationMenuLink asChild>
          <Link
            to={href}
            ref={ref}
            className={cn(
              "block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:text-accent-foreground",
              className
            )}
            {...props}
          >
            <div className="text-sm font-medium leading-none">{title}</div>
            <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
              {children}
            </p>
          </Link>
        </NavigationMenuLink>
      </li>
    );
  }
);
ListItem.displayName = "ListItem";
