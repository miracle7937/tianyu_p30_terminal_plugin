import 'p30_terminal_plugin_platform_interface.dart';

class P30TerminalPlugin {
  Future<String?> getPlatformVersion() {
    return P30TerminalPluginPlatform.instance.getPlatformVersion();
  }

  Future<String?> prep(String terminalID) {
    return P30TerminalPluginPlatform.instance.prep(terminalID);
  }

  Future<void> pay(Map transactionData) {
    return P30TerminalPluginPlatform.instance.pay(transactionData);
  }
}
