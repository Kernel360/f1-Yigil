export const plusMarker = (name: string) => {
  return {
    content: `<div style="transform: translate(-50%, -100%); background-color: #3B82F6; border: 1px #3B82F6 solid; border-radius: 5px;">
      <div style="display: flex; padding: 8px">
        <div style="max-width: 215px; padding:0px 8px 0px 0px; color: #FFFFFF; font-size: 18px; font-weight: 600; text-align: center;">
          <div style="display: -webkit-box; -webkit-box-orient: vertical; -webkit-line-clamp: 2; word-break: keep-all; overflow: hidden; text-overflow: ellipsis;">
            ${name}
          </div>
        </div>
        <div style="display:flex; justify-content: center; align-items: center;">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <rect width="24" height="24" rx="12" fill="#60A5FA"/>
            <path d="M12 6.16663V17.8333" stroke="white" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
            <path d="M6.16797 12H17.8346" stroke="white" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
      </div>
      <div style="position:absolute; background-color: #3B82F6; width: 0.75rem; height: 0.75rem; bottom: -7px; left: 0; right: 0; margin: 0 auto; border-bottom: 1px #60a5fa solid; border-right:1px #60a5fa solid; border-bottom-right-radius: 3px; transform: rotate(45deg);"></div>
    </div>`,
    anchor: new naver.maps.Point(0, 0),
  };
};
