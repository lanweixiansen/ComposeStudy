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

  void onBackPress() {
    _channel.invokeMethod<void>('onBackPress');
  }

  void logout() {
    _channel.invokeMethod<void>('logout');
  }

  Future<String> getCacheSize() async {
    try {
      // 调用原生方法并等待返回结果
      final String data = await _channel.invokeMethod('getCacheSize');

      // 处理返回的数据
      return data;
    } catch (e) {
      return "0.00KB";
    }
  }

  Future<String> getAppVersionName() async {
    try {
      // 调用原生方法并等待返回结果
      final String data = await _channel.invokeMethod('version_name');

      // 处理返回的数据
      return data;
    } catch (e) {
      return "v.0.0";
    }
  }

  void clearCache() {
    _channel.invokeMethod<void>('clearCacheSize');
  }
}
