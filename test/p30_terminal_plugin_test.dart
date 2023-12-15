import 'package:flutter_test/flutter_test.dart';
import 'package:p30_terminal_plugin/p30_terminal_plugin.dart';
import 'package:p30_terminal_plugin/p30_terminal_plugin_platform_interface.dart';
import 'package:p30_terminal_plugin/p30_terminal_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockP30TerminalPluginPlatform
    with MockPlatformInterfaceMixin
    implements P30TerminalPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final P30TerminalPluginPlatform initialPlatform = P30TerminalPluginPlatform.instance;

  test('$MethodChannelP30TerminalPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelP30TerminalPlugin>());
  });

  test('getPlatformVersion', () async {
    P30TerminalPlugin p30TerminalPlugin = P30TerminalPlugin();
    MockP30TerminalPluginPlatform fakePlatform = MockP30TerminalPluginPlatform();
    P30TerminalPluginPlatform.instance = fakePlatform;

    expect(await p30TerminalPlugin.getPlatformVersion(), '42');
  });
}
