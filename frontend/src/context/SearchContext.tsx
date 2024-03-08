// 'use client';

// import { ReactNode, createContext, useReducer } from 'react';

// type TSearchState = {
//   history: string[];
//   keyword: string;
//   result:
//     | { from: 'none' }
//     | { from: 'searchEngine'; content: string[] }
//     | {
//         from: 'backend';
//         content:
//           | { type: 'place'; data: string[] }
//           | { type: 'course'; data: string[] };
//       };
// };

// type TSearchAction = {
//   type: 'INITIAL';
//   payload: any;
// };

// const initialState = {
//   history: [],
//   keyword: '',
//   result: { from: 'none' },
// };

// export const SearchContext = createContext<TSearchState>({
//   history: [],
//   keyword: '',
//   result: { from: 'none' },
// });

// export default function SearchProvider({
//   children,
// }: {
//   state: TSearchState;
//   children: ReactNode;
// }) {
//   const [state, dispatch] = useReducer(searchReducer, initialState);

//   return <SearchContext.Provider>{children}</SearchContext.Provider>;
// }

// function searchReducer(
//   state: TSearchState,
//   action: TSearchAction,
// ): TSearchState {
//   switch (action.type) {
//     case 'INITIAL': {
//       return { ...state };
//     }
//   }
// }
