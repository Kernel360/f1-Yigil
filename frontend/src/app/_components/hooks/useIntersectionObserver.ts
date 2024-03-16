'use client';

import { MutableRefObject, useEffect } from 'react';

function useIntersectionObserver(
  ref: MutableRefObject<Element | null>,
  callback: () => void,
  hasnext: boolean,
  threshold: number = 0.5,
) {
  useEffect(() => {
    let timer: NodeJS.Timeout;
    const observer = new IntersectionObserver(
      async ([entry]) => {
        if (entry.isIntersecting) {
          timer = setTimeout(() => {
            callback();
          }, 100);
        }
      },
      { threshold },
    );
    if (!ref.current) return;

    observer.observe(ref.current);
    if (!hasnext) observer.unobserve(ref.current);
    return () => {
      clearTimeout(timer);
      observer && observer.disconnect();
    };
  }, [callback, ref, threshold, hasnext]);
}

export default useIntersectionObserver;
