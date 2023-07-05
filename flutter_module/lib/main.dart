import 'package:date_format/date_format.dart';
import 'package:flutter/material.dart';
import 'package:my_flutter/data/AppleShopBean.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        brightness: Brightness.light,
        primaryColor: Colors.cyan,
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: AppleShopUpdateWidget(
        model: AppleShopModel(
            appIcon: "assets/icon.png",
            appDescription:
            "Thanks for using Google Maps! This release brings bug fixes that improve our product to help you discover new places and navigate to them.",
            appName: "Google Maps - Transit & Fond",
            appSize: "137.2",
            appVersion: "Version 5.19",
            appDate: "2019年6月5日"),
        onPressed: () {
          print(formatDate(DateTime.now(), ['第', m, '月第', w, '周']));
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

/// 仿苹果商店布局页面Widget
class AppleShopUpdateWidget extends StatelessWidget {
  AppleShopModel? model;
  int line = 1;
  final VoidCallback? onPressed;

  AppleShopUpdateWidget({super.key, this.model, this.onPressed});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [shopTopWidget(context), shopBottomWidget(context)],
    );
  }

  /// 更新布局上部分
  Widget shopTopWidget(BuildContext context) {
    return Row(
      children: [
        Padding(
            padding: const EdgeInsets.all(10),
            child: ClipRRect(
                borderRadius: BorderRadius.circular(8.0),
                child: Image.asset(
                  model!.appIcon,
                  width: 70,
                  height: 70,
                ))),
        Expanded(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(model!.appName,
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(fontSize: 16, color: Color(0xFF000000))),
                Text(model!.appDate,
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(fontSize: 12, color: Color(0xFF8E8D92)))
              ],
            )),
        Padding(
          padding: const EdgeInsets.fromLTRB(8, 0, 10, 0),
          child: TextButton(
            style: ButtonStyle(
                backgroundColor:
                MaterialStateProperty.all(const Color(0xFFF1F0F7)),
                shape: MaterialStateProperty.all(RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20.0)))),
            onPressed: onPressed,
            child: const Text(
              "Update",
              style: TextStyle(
                  color: Color(0xFF007AFE), fontWeight: FontWeight.bold),
            ),
          ),
        )
      ],
    );
  }

  /// 更新布局下部分
  Widget shopBottomWidget(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.fromLTRB(15, 0, 15, 0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              model!.appDescription,
              maxLines: line,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(0, 10, 0, 10),
              child: Text("${model!.appVersion} • ${model!.appSize} MB"),
            )
          ],
        ));
  }
}

///
///
///
///
///

/// 列表Widget
class ListWidget extends StatelessWidget {
  const ListWidget({super.key});

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return ListView.builder(
        itemCount: 50,
        itemExtent: 50.0,
        itemBuilder: (BuildContext context, int index) => ListTile(
          title: Text("这是第$index个"),
          subtitle: Text("body$index"),
        ));
  }
}

/// 文字Widget
class ColumnTextWidget extends StatelessWidget {
  final int _counter = 0;
  TextStyle textBlackStyle =
  const TextStyle(color: Colors.black, fontStyle: FontStyle.italic);
  TextStyle textRedStyle =
  const TextStyle(color: Colors.red, fontWeight: FontWeight.bold);
  TextStyle textStyle = const TextStyle(color: Colors.cyan);

  ColumnTextWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        const Text(
          'You have pushed the button this many times:',
          style: TextStyle(
              color: Colors.cyan, fontSize: 20, fontWeight: FontWeight.bold),
        ),
        Text(
          '$_counter',
          style: Theme.of(context).textTheme.headlineMedium,
        ),
        Text.rich(TextSpan(children: <TextSpan>[
          TextSpan(text: "这是", style: textStyle),
          TextSpan(text: "黑色斜体，", style: textBlackStyle),
          TextSpan(text: "这是", style: textStyle),
          TextSpan(text: "红色粗体。", style: textRedStyle)
        ]))
      ],
    );
  }
}
