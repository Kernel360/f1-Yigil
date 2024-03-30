'use client';
import { useState, useEffect } from 'react';
import * as Toast from '@radix-ui/react-toast';

interface TToast {
  title?: string;
  description?: string;
  timer?: number;
  id?: number;
}

export default function ToastMsg({ title, description, timer, id }: TToast) {
  const [open, setOpen] = useState(false);

  useEffect(() => {
    setOpen(true);
    const timerFunc = setTimeout(() => {
      setOpen(false);
    }, timer);
    return () => {
      clearTimeout(timerFunc);
    };
  }, [id, timer]);

  return (
    <Toast.Provider swipeDirection="right">
      <Toast.Root
        className={`bg-[#7F8288] rounded-xl shadow-[hsl(206_22%_7%_/_35%)_0px_10px_38px_-10px,_hsl(206_22%_7%_/_20%)_0px_10px_20px_-15px] flex justify-center items-center data-[state=open]:animate-slideIn data-[state=closed]:animate-hide data-[swipe=move]:translate-x-[var(--radix-toast-swipe-move-x)] data-[swipe=cancel]:translate-x-0 data-[swipe=cancel]:transition-[transform_200ms_ease-out] data-[swipe=end]:animate-swipeOut text-pretty ${
          open && 'animate-appear'
        } py-4 px-6`}
        open={open}
        onOpenChange={setOpen}
      >
        <Toast.Title className="[grid-area:_title] font-bold leading-5 text-white break-normal">
          {title}
        </Toast.Title>
        <Toast.Description asChild>{description}</Toast.Description>
      </Toast.Root>
      <Toast.Viewport className="fixed bottom-20 left-[50%] -translate-x-[50%] flex flex-col p-[var(--viewport-padding)] gap-[10px] my-0 list-none z-[2147483647] outline-none min-w-[330px] max-w-[390px]" />
    </Toast.Provider>
  );
}
