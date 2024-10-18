import React, {useEffect} from 'react';
import {
  ActivityIndicator,
  Alert,
  NativeModules,
  SafeAreaView,
  StatusBar,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import WebView from 'react-native-webview';
import NetInfo, {useNetInfo} from '@react-native-community/netinfo';
const { ImmersiveMode } = NativeModules;

function App(): React.JSX.Element {
  const uri = 'https://auto.takeat.app/';
  const [internet, setInternet] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const netInfo = useNetInfo();
  useEffect(() => {
    if (netInfo && netInfo.isConnected) {
      setInternet(netInfo.isConnected);
    }
  }, [netInfo]);
  const onMessage = payload => {
    console.log('payload', payload.nativeEvent.data);
  };
  const immersiveMode = () => {
    ImmersiveMode.enterLeanBackMode();
    console.log('ImmersiveMode', ImmersiveMode);
  };
  useEffect(() => {
    immersiveMode();
  }, []);
  return (
    <SafeAreaView style={{flex: 1}}>
      <StatusBar hidden={true} />
      {!netInfo.isConnected && !internet && (
        <View
          style={{
            flex: 1,
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <Text>Type: {netInfo.type}</Text>
          <Text style={{fontSize: 28}}>
            Dispositivo não conectado a Internet.
          </Text>
          <TouchableOpacity
            disabled={loading}
            style={{height: 60}}
            onPress={() => {
              NetInfo.fetch().then(state => {
                setLoading(true);
                setTimeout(() => {
                  setLoading(false);
                }, 2000);
                setInternet(state.isConnected);
              });
            }}>
            <Text
              style={{
                fontSize: 20,
                marginTop: 16,
                textDecorationLine: 'underline',
              }}>
              {loading ? (
                <ActivityIndicator size="small" color="#666" />
              ) : (
                'Tentar conexão'
              )}
            </Text>
          </TouchableOpacity>
        </View>
      )}
      {internet && (
        <WebView
          // ref={webview => {
          //   this.webview = webview;
          // }}
          onMessage={onMessage}
          originWhitelist={['*']}
          javaScriptEnabledAndroid={true}
          source={{uri: uri}}
          onError={() => {
            Alert.alert(
              'Error',
              'Algo de errado aconteceu.\n Verifique sua conexão com a internet.',
            );
          }}
        />
      )}
    </SafeAreaView>
  );
}

export default App;
