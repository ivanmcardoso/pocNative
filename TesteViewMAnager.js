import PropTypes from 'prop-types';
import { requireNativeComponent, ViewPropTypes, findNodeHandle, Button } from 'react-native';

var viewProps = {
    name: 'RTCImageViewer',
    propTypes: {
      url: PropTypes.string,
      ...ViewPropTypes,
    }
  }
  module.exports = requireNativeComponent('RTCImageViewer', viewProps);
  