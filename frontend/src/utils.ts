export function dataUrlToBlob(dataURI: string) {
  const byteString = atob(dataURI.split(',')[1]);

  // separate out the mime component
  const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

  // write the bytes of the string to an ArrayBuffer
  const ab = new ArrayBuffer(byteString.length);
  const ia = new Uint8Array(ab);
  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ab], { type: mimeString });
}

export async function blobTodataUrl(blob: Blob) {
  const buffer = Buffer.from(await blob.arrayBuffer());

  return `data:${blob.type};base64,${buffer.toString('base64')}`;
}