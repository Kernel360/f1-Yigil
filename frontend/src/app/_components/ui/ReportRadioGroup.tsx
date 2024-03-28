import * as RadioGroup from '@radix-ui/react-radio-group';

import type { Dispatch, SetStateAction } from 'react';
import type { TReportType } from '@/types/response';

export default function ReportRadioGroup({
  reportTypes,
  reportTypeId,
  setReportTypeId,
}: {
  reportTypes?: TReportType[];
  reportTypeId: number;
  setReportTypeId: Dispatch<SetStateAction<number>>;
}) {
  return (
    <RadioGroup.Root
      className="p-4 flex flex-col items-center gap-4 grow"
      value={reportTypeId.toString()}
      onValueChange={(value) => setReportTypeId(Number.parseInt(value, 10))}
    >
      {reportTypes?.map(({ id, name }) => (
        <div className="w-full flex items-center gap-3" key={id}>
          <RadioGroup.Item
            className="p-[2px] flex bg-white w-6 h-6 rounded-full border-[1.5px] border-gray-300 aria-checked:border-blue-500"
            id={id.toString()}
            value={id.toString()}
          >
            <RadioGroup.Indicator className="flex items-center justify-center w-full h-full rounded-full relative bg-blue-500" />
          </RadioGroup.Item>
          <label className="text-lg font-light" htmlFor={id.toString()}>
            {name}
          </label>
        </div>
      ))}
    </RadioGroup.Root>
  );
}
