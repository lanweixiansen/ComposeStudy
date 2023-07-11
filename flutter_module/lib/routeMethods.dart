import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class CounterModel extends ChangeNotifier {
  final _channel = const MethodChannel('dev.flutter.example/route');

  void increment(String route) {
    _channel.invokeMethod<void>('routeActivity', {'data': route});
  }

  void showToast(String toast) {
    _channel.invokeMethod<void>('showToast', {'toast': toast});
  }
}
