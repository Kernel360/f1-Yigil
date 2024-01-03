import { forwardRef } from 'react';

const SvgrMock = forwardRef((props, ref) => <svg ref={ref} {...props} />);

export const ReactComponent = SvgrMock;
export default SvgrMock;
