'use client';

import { createContext, useState } from 'react';

import type { ReactElement } from 'react';

import type { TReportType } from '@/types/response';

export const ReportContext = createContext<TReportType[] | undefined>([]);

export default function ReportProvider({
  backendReportTypes,
  children,
}: {
  backendReportTypes?: TReportType[];
  children: ReactElement;
}) {
  const [reportTypes] = useState(backendReportTypes);

  return (
    <ReportContext.Provider value={reportTypes}>
      {children}
    </ReportContext.Provider>
  );
}
