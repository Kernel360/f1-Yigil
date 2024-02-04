import { ComponentType } from 'react';

export interface TPopOverData {
  href?: string;
  label: string;
  Icon: ComponentType<{ className?: string }>;
  onClick?: () => void;
}
