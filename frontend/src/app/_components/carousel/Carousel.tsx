'use client';
import Image from 'next/image';
import React, { useEffect, useState } from 'react';

export default function Carousel() {
  /**
   * ssr 
      const res = await fetch(`http://localhost:8080/api/event`);
      const result = await res.json();
      const eventImgs: string[] = await result.data;
    */

  const [eventImgs, setEventImgs] = useState<string[]>([]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/event`)
      .then((res) => res.json())
      .then((result) => setEventImgs(result.data));
  }, []);

  console.log(eventImgs);
  return (
    <div className="flex overflow-hidden">
      {eventImgs.length > 0 &&
        eventImgs.map((img, idx) => (
          <Image
            key={idx}
            src={img}
            alt="event-image"
            width={430}
            height={300}
          />
        ))}
    </div>
  );
}
