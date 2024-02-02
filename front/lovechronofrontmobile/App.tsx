import config from './tamagui.config';
import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import { TamaguiProvider, YStack } from 'tamagui';
import { User } from './src/components/User';

export default function App() {
  return (
   <TamaguiProvider config={config}>
    <YStack bg="$red10" f={1}>
      <User/>
    </YStack>
   </TamaguiProvider>
  );
}

