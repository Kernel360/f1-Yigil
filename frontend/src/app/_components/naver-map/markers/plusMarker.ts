export const plusMarker = (place: { name: string }) => {
  return {
    content: `<div style=" border: 1px #60a5fa solid; border-radius: 5px; background-color: #fff; word-break:keep-all;">
  <div style="display: flex; padding: 8px 10px">
  <div style="padding:0px 8px 0px 0px; color:#374151; font-size: 18px; font-weight: 600; text-align: center;">${place.name}</div>
  <div style="display:flex; justify-content: center; align-items: center;">
  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
  <rect width="24" height="24" rx="12" fill="#60A5FA"/>
  <path d="M12 6.16663V17.8333" stroke="white" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
  <path d="M6.16797 12H17.8346" stroke="white" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
  </svg>
  </div>
  </div>
  <div style="position:absolute; bottom:-5px; left:50px; width: 12px; height:12px;background-color:#fff; border-bottom: 1px #60a5fa solid; border-right:1px #60a5fa solid; transform: rotate(45deg);"></div>
  </div>`,
    anchor: new naver.maps.Point(50, 50),
  };
};
