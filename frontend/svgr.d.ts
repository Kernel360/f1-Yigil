declare module '*.svg' {
  import { FC, SVGProps } from 'react';
  const content: FC<SVGProps<SVGElement>> & { className: string };
  export default content;
}

declare module '*.svg?url' {
  const content: {
    src: string;
    width: number;
    height: number;
    blurWidth: number;
    blurHeight: number;
  };
  export default content;
}
