import * as Making from './makingStep';
import * as DataInput from './dataInput';

export interface TStep {
  makingStep: Making.TMakingStep;
  inputStep: DataInput.TDataInputStep;
}

export type { Making, DataInput };
