import Marker1 from '/public/icons/markers/marker1.svg?url';
import Marker2 from '/public/icons/markers/marker2.svg?url';
import Marker3 from '/public/icons/markers/marker3.svg?url';
import Marker4 from '/public/icons/markers/marker4.svg?url';
import Marker5 from '/public/icons/markers/marker5.svg?url';

export function getNumberMarker(num: number) {
  if (num === 1) {
    return Marker1.src;
  }

  if (num === 2) {
    return Marker2.src;
  }

  if (num === 3) {
    return Marker3.src;
  }

  if (num === 4) {
    return Marker4.src;
  }

  if (num === 5) {
    return Marker5.src;
  }

  return '';
}
