import React, { useEffect, useRef } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';
import ImgView from './RTCImageViewer'
import {TesteModule} from './TesteModule'

export default function App() {

  const reff = useRef(null)

  return (
    <View style={styles.container}>
      <Text>Open up App.tsx to start working on your app!</Text>
      <ImgView />
      <Button title="buton" onPress={()=>{
        TesteModule.abreAi()
      }}/>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
