import 'package:flutter/material.dart';
import 'package:my_flutter/AnimsWidget.dart';
import 'package:my_flutter/route/routeMethods.dart';
import 'package:my_flutter/setting.dart';
import 'package:provider/provider.dart';

import 'ListTitle.dart';
import 'data/Route.dart';
import 'ios.dart';
import 'normalWidget.dart';

class MineWidget extends StatelessWidget {
  const MineWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        "ios_page": (context) => const IosWidget(),
        "normal_widget": (context) => const NormalWidget(),
        "setting_widget": (context) => const SettingWidget(),
        "list_title": (context) => const ListTileApp(),
        "logo_app": (context) => const LogoApp(),

      },
      theme: ThemeData(
        useMaterial3: true,
      ),
      home: _MineHomePage(),
    );
  }
}

class _MineHomePage extends StatelessWidget {
  final pressModel = CounterModel();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        actions: [
          IconButton(
              onPressed: () {
                pressModel.increment("setting_widget");
              },
              icon: const Icon(Icons.settings))
        ],
        backgroundColor: Colors.white,
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          _MineTopPage(),
          const Padding(
              padding: EdgeInsets.only(top: 20, left: 20, bottom: 16),
              child: Text('Flutter',
                  style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.black87))),
          _MineBottomPage()
        ],
      ),
    );
  }
}

class _MineTopPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        const Padding(
            padding: EdgeInsets.only(top: 17),
            child: CircleAvatar(
              backgroundImage: AssetImage('assets/image/img6.jpg'),
              radius: 50,
            )),
        const Padding(
            padding: EdgeInsets.only(top: 14),
            child: Text(
              '爷傲奈我何',
              style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  color: Colors.black),
            )),
        const Padding(
          padding: EdgeInsets.only(top: 8),
          child: Text(
            '这世界不停开花，我想放你心里一朵。',
            style: TextStyle(color: Colors.black54, fontSize: 14),
          ),
        ),
        Container(
          margin: const EdgeInsets.fromLTRB(20, 27, 20, 20),
          height: 80,
          width: double.infinity,
          decoration: BoxDecoration(borderRadius: BorderRadius.circular(18)),
          child: ClipRRect(
            borderRadius: BorderRadius.circular(8),
            child: const Image(
              image: AssetImage('assets/image/img11.jpg'),
              fit: BoxFit.cover,
            ),
          ),
        )
      ],
    );
  }
}

class _MineBottomPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Expanded(
        child: ListView.builder(
            itemCount: routeList.length,
            itemExtent: 70.0,
            itemBuilder: (BuildContext context, int index) =>
                Consumer<CounterModel>(
                  builder: (context, model, child) {
                    return GestureDetector(
                        onTap: () {
                          // 原生跳转方法
                          model.increment(routeList[index].route);
                          // 本地跳转方法
                          Navigator.pushNamed(context, routeList[index].route);
                        },
                        child: Container(
                          padding: const EdgeInsets.only(left: 4, right: 8),
                          margin: const EdgeInsets.only(
                              left: 16, right: 16, bottom: 8),
                          decoration: BoxDecoration(
                              color: Colors.white,
                              borderRadius: BorderRadius.circular(8)),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.stretch,
                            children: [
                              ClipRRect(
                                borderRadius: BorderRadius.circular(8),
                                child: Image(
                                  image: AssetImage(routeList[index].image),
                                  fit: BoxFit.cover,
                                ),
                              ),
                              const Padding(padding: EdgeInsets.only(left: 8)),
                              Column(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceEvenly,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text("${index + 1}"),
                                  Text(
                                    routeList[index].title,
                                    style: const TextStyle(
                                        fontSize: 16,
                                        fontWeight: FontWeight.bold,
                                        color: Colors.black87),
                                  ),
                                ],
                              )
                            ],
                          ),
                        ));
                  },
                )));
  }
}
