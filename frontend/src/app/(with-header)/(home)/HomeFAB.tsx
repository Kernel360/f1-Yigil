import { homePopOverData } from '@/app/_components/ui/popover/constants';
import FloatingActionButton from '@/app/_components/FloatingActionButton';

import PlusIcon from '@/../public/icons/plus.svg';
import EditIcon from '@/../public/icons/edit.svg';

function OpenedFABIcon() {
  return <PlusIcon className="w-9 h-9 rotate-45 z-30" />;
}

function ClosedFABIcon() {
  return (
    <EditIcon className="w-12 h-12 fill-none stroke-white stroke-[1.5px]" />
  );
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
