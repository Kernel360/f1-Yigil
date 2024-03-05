export const basicMarker = (name: string) => {
  return {
    content: `<div style=" border: 1px #374151 solid; border-radius: 5px; background-color: #fff; word-break:keep-all;">
    <div style="display: flex; padding: 8px 10px">
    <div style="padding:0px 0px 0px 0px; color:#374151; font-size: 18px; font-weight: 600; text-align: center;">${name}</div>
    <div style="display:flex; justify-content: center; align-items: center;">
    </div>
    </div>
    <div style="position:absolute; bottom:-5px; left: 50%; transform: translateX(-50%);  width: 12px; height:12px;background-color:#fff; border-bottom: 1px #374151 solid; border-right:1px #374151 solid; transform: rotate(45deg);"></div>
    </div>`,
    anchor: new naver.maps.Point(50, 50),
  };
};
