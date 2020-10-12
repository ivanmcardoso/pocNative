import React, { useEffect, useRef, useState } from 'react';
import {TesteModule} from './TesteModule'

import PropTypes from 'prop-types';
import { requireNativeComponent, ViewPropTypes, findNodeHandle, Button } from 'react-native';
import RTCImageViewer from './TesteViewMAnager'

const ImgView: React.FC = () => {
      const mRef = useRef(null)
      const [previewHandler, setPreviewHandler] = useState(null)
      useEffect(()=>{
        if(mRef?.current){
          const nodeHandle = findNodeHandle(mRef?.current) 
          console.log(nodeHandle);
          setPreviewHandler(nodeHandle)
      }
      }, [mRef])

      async function teste(){
        if (previewHandler) {
          try {
            console.log("antes");
            const res = await TesteModule.startCamera(previewHandler);
            console.log("Suc: ", res);
          } catch (error) {
            console.log(error);
          }
        }
      }

      return (
        <>
      <RTCImageViewer ref={mRef} style={{ flex: 1, width: '100%', height: '50%', borderWidth:1 }} />
        <Button title="teste" onPress={teste}/>
      </>
      )

}

export default ImgView;
