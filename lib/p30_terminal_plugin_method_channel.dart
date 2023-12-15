import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'p30_terminal_plugin_platform_interface.dart';

/// An implementation of [P30TerminalPluginPlatform] that uses method channels.
class MethodChannelP30TerminalPlugin extends P30TerminalPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('p30_terminal_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> prep(String terminalID) async {
    final version =
        await methodChannel.invokeMethod<String>('prep', terminalID);
    return version;
  }

  @override
  Future<void> pay(Map transactionData) async {
    final payAmount = await methodChannel
        .invokeListMethod("pay", {"transactionData": transactionData});
  }
}
