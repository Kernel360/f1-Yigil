import { TCourseState } from '../schema';

export const initialCourseState: TCourseState = {
  courseReview: { title: '', content: '', rate: 1 },
  spots: [],
};

export interface TCourseAction {
  type: 'ADD_PLACE' | 'REMOVE_PLACE';
  payload?: unknown;
}

export default function reducer(
  state: TCourseState,
  action: TCourseAction,
): TCourseState {
  switch (action.type) {
    case 'ADD_PLACE': {
      return { ...state };
    }
    case 'REMOVE_PLACE': {
      return { ...state };
    }
  }
}
