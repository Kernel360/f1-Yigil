import { ComponentType, ReactElement } from 'react';

export interface TPopOverData {
  href?: string;
  label: string;
  icon: ReactElement
  onClick?: () => void;
}
