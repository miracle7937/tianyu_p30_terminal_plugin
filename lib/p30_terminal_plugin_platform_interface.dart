import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'p30_terminal_plugin_method_channel.dart';

abstract class P30TerminalPluginPlatform extends PlatformInterface {
  /// Constructs a P30TerminalPluginPlatform.
  P30TerminalPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static P30TerminalPluginPlatform _instance = MethodChannelP30TerminalPlugin();

  /// The default instance of [P30TerminalPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelP30TerminalPlugin].
  static P30TerminalPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [P30TerminalPluginPlatform] when
  /// they register themselves.
  static set instance(P30TerminalPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> prep(String terminalID) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> pay(Map transactionData) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
