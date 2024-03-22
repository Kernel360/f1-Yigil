export const basicMarker = (
  name: string,
  selected?: boolean,
): naver.maps.HtmlIcon => {
  return {
    content: `<div style="min-width: 85px; max-width: 215px; transform: translate(-50%, -100%); background-color: ${
      selected ? '#3B82F6' : '#FFFFFF'
    }; border: 1px #3B82F6 solid; border-radius: 5px;">
      <div style="padding: 8px;">
        <div style="display: -webkit-box; -webkit-box-orient: vertical; -webkit-line-clamp: 2; color: ${
          selected ? '#FFFFFF' : '#374151'
        }; overflow: hidden; text-overflow: ellipsis; font-size: 18px; font-weight: 600; text-align: center;">${name}</div>
        </div>
        <div style="position: absolute; width: 0.75rem; height: 0.75rem; bottom: -7px; left: 0; right: 0; margin: 0 auto; background-color: ${
          selected ? '#3B82F6' : '#FFFFFF'
        }; border-bottom: 1px #3B82F6 solid; border-right:1px #3B82F6 solid; border-bottom-right-radius: 3px; transform: rotate(45deg);"></div>
      </div>
    </div>`,
    anchor: new naver.maps.Point(0, 0),
  };
};
