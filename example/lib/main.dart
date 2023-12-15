import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:p30_terminal_plugin/p30_terminal_plugin.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _p30TerminalPlugin = P30TerminalPlugin();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    String platformVersion;

    try {
      platformVersion = await _p30TerminalPlugin.getPlatformVersion() ??
          'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  var data = {
    "businessName": "Elbuhaj Royal Academy",
    "businessID": "220437",
    "tid": "2101KI58",
    "amount": "1",
    "accountType": "00"
  };

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              Text('Running on: $_platformVersion\n'),
              ElevatedButton(
                onPressed: () {
                  _p30TerminalPlugin.prep("2ETP0012");
                },
                child: Text('Inject Keys'),
              ),
              ElevatedButton(
                onPressed: () {
                  _p30TerminalPlugin.pay(data);
                },
                child: Text('Payment'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
