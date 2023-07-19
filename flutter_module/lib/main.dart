import 'package:flutter/material.dart';
import 'package:my_flutter/ListTitle.dart';
import 'package:my_flutter/data/Route.dart';
import 'package:my_flutter/ios.dart';
import 'package:my_flutter/normalWidget.dart';
import 'package:my_flutter/route/routeMethods.dart';
import 'package:my_flutter/setting.dart';
import 'package:provider/provider.dart';

void main() {
  final model = CounterModel();

  runApp(
    ChangeNotifierProvider.value(
      value: model,
      child: const ListTileApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        "ios_page": (context) => const IosWidget(),
        "normal_widget": (context) => const NormalWidget(),
        "setting_widget": (context) => const SettingWidget(),
        "list_title": (context) => const ListTileApp(),
      },
      theme: ThemeData(
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo'),
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
    final pressModel = CounterModel();
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        centerTitle: true,
        actions: [
          IconButton(
              onPressed: () {
                pressModel.increment("setting_widget");
              },
              icon: const Icon(Icons.settings))
        ],
      ),
      body: const ListWidget(),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}

/// 列表Widget

class ListWidget extends StatelessWidget {
  const ListWidget({super.key});

  @override
  Widget build(BuildContext context) {
    /// 默认创建方法
    // return ListView.builder(
    //     itemCount: 50,
    //     itemExtent: 80.0,
    //     itemBuilder: (BuildContext context, int index) => ListTile(
    //           title: Text("这是第$index个"),
    //           subtitle: Text("$index"),
    //         ));
    /// 展示分割线
    // return ListView.separated(
    //     itemBuilder: (BuildContext context, int index) => ListTile(
    //           title: Text("这是第$index 个"),
    //           subtitle: Text("$index"),
    //         ),
    //     separatorBuilder: (BuildContext context, int index) => const Divider(
    //           color: Colors.blue,
    //         ),
    //     itemCount: 20);
    /// 展示分割线2
    return ListView.builder(
        itemCount: routeList.length,
        itemExtent: 80.0,
        itemBuilder: (BuildContext context, int index) =>
            Consumer<CounterModel>(
              builder: (context, model, child) {
                return GestureDetector(
                    onTap: () {
                      // 原生跳转方法
                      model.increment(routeList[index].route);
                      // 本地跳转方法
                      // Navigator.pushNamed(context, "normal_widget");
                    },
                    child: Container(
                      padding: const EdgeInsets.only(left: 8, right: 8),
                      margin:
                          const EdgeInsets.only(left: 16, right: 16, bottom: 8),
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(8),
                          border: Border.all(color: Colors.black, width: 1)),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text("${index + 1}"),
                          Text(routeList[index].title),
                          Container(
                            height: 1,
                            color: Colors.blueAccent,
                          )
                        ],
                      ),
                    ));
              },
            ));
  }
}
