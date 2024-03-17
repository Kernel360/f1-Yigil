export const basicMarker = (name: string, selected?: boolean) => {
  return {
    content: `<div style="border: 1px #3B82F6 solid; border-radius: 5px; background-color: ${
      selected ? '#3B82F6' : '#FFFFFF'
    }; white-space: nowrap;">
    <div style="display: flex; padding: 8px 10px; ">
    <div style="padding:0px 0px 0px 0px; color: ${
      selected ? '#FFFFFF' : '#374151'
    }; font-size: 18px; font-weight: 600; text-align: center;">${name}</div>
    <div style="display:flex; justify-content: center; align-items: center;">
    </div>
    </div>
    <div style="position:absolute; bottom:-6.3px; left: 45%; width: 12px; height:12px; background-color: ${
      selected ? '#3B82F6' : '#FFFFFF'
    }; border-bottom: 1px #3B82F6 solid; border-right:1px #374151 solid; border-bottom-right-radius: 3px; transform: rotate(45deg);"></div>
    </div>`,
    anchor: new naver.maps.Point(50, 50),
  };
};
