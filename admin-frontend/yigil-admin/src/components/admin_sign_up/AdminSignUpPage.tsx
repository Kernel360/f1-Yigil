import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import withAuthProtection from "../snippet/withAuthProtection";

import {
  ChevronDownIcon,
  DotsHorizontalIcon,
  RocketIcon,
} from "@radix-ui/react-icons";

import {
  ColumnDef,
  ColumnFiltersState,
  VisibilityState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from "@tanstack/react-table";

import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

export type AdminSignUp = {
  id: string;
  email: string;
  nickname: string;
  request_datetime: string;
};

export const columns: ColumnDef<AdminSignUp>[] = [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={table.getIsAllPageRowsSelected()}
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "email",
    header: "이메일",
    cell: ({ row }) => <div>{row.getValue("email")}</div>,
  },
  {
    accessorKey: "nickname",
    header: "닉네임",
    cell: ({ row }) => <div>{row.getValue("nickname")}</div>,
  },
  {
    accessorKey: "request_datetime",
    header: "요청 시간",
    cell: ({ row }) => <div>{row.getValue("request_datetime")}</div>,
  },
  {
    id: "actions",
    enableHiding: false,
    cell: ({ row }) => {
      const adminSignUp = row.original;

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Open menu</span>
              <DotsHorizontalIcon className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem
              onClick={() => navigator.clipboard.writeText(adminSignUp.email)}
            >
              이메일 복사하기
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem>가입 승인</DropdownMenuItem>
            <DropdownMenuItem>가입 거절</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      );
    },
  },
];

const AdminSignUpPage: React.FC = () => {
  const [adminSignUps, setAdminSignUps] = useState<AdminSignUp[]>([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [columnFilters, setColumnFilters] = React.useState<ColumnFiltersState>(
    []
  );
  const [columnVisibility, setColumnVisibility] =
    React.useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = React.useState<{
    [key: string]: boolean;
  }>({});

  const [adminSignUpIds, setAdminSignUpIds] = useState([]);

  const handlePageClick = (newPage: number) => {
    setPage(newPage);
  };

  const getSelectedRowIds = () => {
    return Object.keys(rowSelection).filter((id) => rowSelection[id]);
  };

  const handleAcceptSelectedSignUps = async () => {
    const selectedRowIds = getSelectedRowIds();
    const accessToken = getCookie("accessToken");

    const selectedIds = selectedRowIds.map(
      (index) => adminSignUpIds[parseInt(index)]
    );

    try {
      const response = await fetch(
        "http://localhost:8081/admin/api/v1/admins/signup/accept",
        {
          method: "POST",
          headers: {
            Authorization: `${accessToken}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ ids: selectedIds }),
        }
      );

      if (!response.ok) {
        const data = await response.json();
        console.log(data);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const createPaginationItems = () => {
    let startPage = Math.max(1, page - 4);
    let endPage = Math.min(totalPages, page + 5);

    if (endPage - startPage > 9) {
      if (page - startPage < 5) {
        endPage = startPage + 9;
      } else {
        startPage = endPage - 9;
      }
    }
    return Array.from(
      { length: endPage - startPage + 1 },
      (_, i) => startPage + i
    );
  };

  useEffect(() => {
    const accessToken = getCookie("accessToken");

    const fetchAdminSignUps = async () => {
      try {
        const response = await fetch(
          `http://localhost:8081/admin/api/v1/admins/signup/list?page=${encodeURIComponent(
            page
          )}&dataCount=10`,
          {
            method: "GET",
            headers: {
              Authorization: `${accessToken}`,
              "Content-Type": "application/json",
            },
          }
        );
        if (!response.ok) {
          throw new Error("서버로부터 데이터를 가져오는데 실패했습니다.");
        }
        const { content, total_pages: newTotalPages } = await response.json();
        setAdminSignUps(content);
        setTotalPages(newTotalPages);

        const ids = content.map((signUp: AdminSignUp) => signUp.id);
        setAdminSignUpIds(ids);
      } catch (error) {
        console.error(error);
      }
    };

    fetchAdminSignUps();
  }, [page]);

  const table = useReactTable({
    data: adminSignUps,
    columns,
    onColumnFiltersChange: setColumnFilters,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    onColumnVisibilityChange: setColumnVisibility,
    onRowSelectionChange: setRowSelection,
    state: {
      columnFilters,
      columnVisibility,
      rowSelection,
    },
  });

  return (
    <Layout>
      <div className="w-[800px] my-10 mx-auto">
        <Alert>
          <RocketIcon className="h-4 w-4" />
          <AlertTitle>어드민 가입 요청 관리</AlertTitle>
          <AlertDescription>
            관리자 서비스 가입 요청을 승인, 거절할 수 있습니다.
          </AlertDescription>
        </Alert>
        <div className="flex items-center py-4">
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="outline" className="ml-auto">
                Columns <ChevronDownIcon className="ml-2 h-4 w-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              {table
                .getAllColumns()
                .filter((column) => column.getCanHide())
                .map((column) => {
                  return (
                    <DropdownMenuCheckboxItem
                      key={column.id}
                      className="capitalize"
                      checked={column.getIsVisible()}
                      onCheckedChange={(value) =>
                        column.toggleVisibility(!!value)
                      }
                    >
                      {column.id}
                    </DropdownMenuCheckboxItem>
                  );
                })}
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
        <div className="rounded-md border">
          <Table>
            <TableHeader>
              {table.getHeaderGroups().map((headerGroup) => (
                <TableRow key={headerGroup.id}>
                  {headerGroup.headers.map((header) => {
                    return (
                      <TableHead key={header.id}>
                        {header.isPlaceholder
                          ? null
                          : flexRender(
                              header.column.columnDef.header,
                              header.getContext()
                            )}
                      </TableHead>
                    );
                  })}
                </TableRow>
              ))}
            </TableHeader>
            <TableBody>
              {table.getRowModel().rows?.length ? (
                table.getRowModel().rows.map((row) => (
                  <TableRow
                    key={row.getValue("email")}
                    data-state={row.getIsSelected() && "selected"}
                  >
                    {row.getVisibleCells().map((cell) => (
                      <TableCell key={cell.id}>
                        {flexRender(
                          cell.column.columnDef.cell,
                          cell.getContext()
                        )}
                      </TableCell>
                    ))}
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell
                    colSpan={columns.length}
                    className="h-24 text-center"
                  >
                    No results.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </div>
        <div className="flex items-center justify-end space-x-2 py-4">
          <div className="flex-1 text-sm text-muted-foreground">
            {table.getFilteredSelectedRowModel().rows.length} of{" "}
            {table.getFilteredRowModel().rows.length} row(s) selected.
          </div>
          <div className="space-x-2">
            <Button
              variant="outline"
              size="sm"
              onClick={handleAcceptSelectedSignUps}
              disabled={table.getFilteredSelectedRowModel().rows.length < 1}
            >
              승인
            </Button>
            <Button
              variant="destructive"
              size="sm"
              onClick={() => table.nextPage()}
              disabled={table.getFilteredSelectedRowModel().rows.length < 1}
            >
              삭제
            </Button>
          </div>
        </div>
        {totalPages > 1 && (
          <Pagination>
            <PaginationContent>
              <PaginationItem
                onClick={() => handlePageClick(Math.max(1, page - 1))}
              >
                <PaginationPrevious />
              </PaginationItem>
              {createPaginationItems().map((pageNum) => (
                <PaginationItem
                  key={pageNum}
                  onClick={() => handlePageClick(pageNum)}
                >
                  <PaginationLink isActive={page === pageNum}>
                    {pageNum}
                  </PaginationLink>
                </PaginationItem>
              ))}
              <PaginationItem
                onClick={() => handlePageClick(Math.min(totalPages, page + 1))}
              >
                <PaginationNext />
              </PaginationItem>
            </PaginationContent>
          </Pagination>
        )}
      </div>
    </Layout>
  );
};

export default withAuthProtection(AdminSignUpPage);

function getCookie(name: string): string | undefined {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop()?.split(";").shift();
}
