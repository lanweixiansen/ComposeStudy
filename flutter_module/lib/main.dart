import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:my_flutter/data/Route.dart';
import 'package:my_flutter/ios.dart';
import 'package:provider/provider.dart';

void main() {
  final model = CounterModel();

  runApp(
    ChangeNotifierProvider.value(
      value: model,
      child: const MyApp(),
    ),
  );
}

class CounterModel extends ChangeNotifier {
  final _channel = const MethodChannel('dev.flutter.example/route');

  void increment(String route) {
    _channel.invokeMethod<void>('routeActivity', {'data': route});
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        "ios_page": (context) => const IosWidget(),
        "": (context) => const IosWidget()
      },
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
      body: const ListWidget(),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
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
                      model.increment(routeList[index].route);
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
                          Text("$index"),
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
