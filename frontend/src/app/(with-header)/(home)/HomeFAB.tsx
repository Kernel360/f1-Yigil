import { homePopOverData } from '@/app/_components/ui/popover/constants';
import FloatingActionButton from '@/app/_components/FloatingActionButton';

import PlusIcon from '@/../public/icons/plus.svg';

function OpenedFABIcon() {
  return <PlusIcon className="w-9 h-9 rotate-45 duration-200 z-30" />;
}

function ClosedFABIcon() {
  return <PlusIcon className="w-9 h-9 rotate-0 duration-200" />;
}

export default function HomeFAB() {
  return (
    <FloatingActionButton
      popOverData={homePopOverData}
      backdropStyle="bg-black bg-opacity-10"
      openedIcon={<OpenedFABIcon />}
      closedIcon={<ClosedFABIcon />}
    />
  );
}
