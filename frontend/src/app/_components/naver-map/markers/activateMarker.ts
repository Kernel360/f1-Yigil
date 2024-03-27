export const activateMarker = (name: string) => {
  return {
    content: `<div style=" border: 1px #3B82F6 solid; border-radius: 5px; background-color: #3B82F6; word-break:keep-all;">
    <div style="display: flex; padding: 8px 10px">
    <div style="padding:0px 8px 0px 0px; color:#ffffff; font-size: 18px; font-weight: 600; text-align: center;">${name}</div>
    <div style="display:flex; justify-content: center; align-items: center;">
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
    <rect width="24" height="24" rx="12" fill="#ffffff"/>
    <path d="M6.16797 12H17.8346" stroke="#3B82F6" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
    <path d="M12 6.16797L17.8333 12.0013L12 17.8346" stroke="#3B82F6" stroke-width="1.25" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>
    </div>
    </div>
    </div>
    <div style="position:fixed; bottom:-6px; left: 45%; width: 12px; height:12px; background-color:#3B82F6; border-bottom: 1px #374151 solid; border-right:1px #374151 solid; border-bottom-right-radius: 3px; transform: rotate(45deg);"></div>
    </div>`,
    anchor: new naver.maps.Point(50, 50),
  };
};
