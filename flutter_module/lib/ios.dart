import 'package:flutter/material.dart';


import 'data/AppleShopBean.dart';

class IosWidget extends StatelessWidget {
  const IosWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        brightness: Brightness.light,
        primaryColor: Colors.cyan,
        useMaterial3: true,
      ),
      home: const MyHomePage(title: '仿写IOS更新布局'),
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blueAccent,
        shadowColor: Colors.white30,
        elevation: 10,
        title: Text(
          title,
          style: const TextStyle(color: Colors.white),
        ),
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
        onPressed: () {},
      ),
    );
  }
}

/// 仿苹果商店布局页面Widget
class AppleShopUpdateWidget extends StatelessWidget {
  AppleShopModel? model;
  int line = 3;
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
              overflow: TextOverflow.ellipsis,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(0, 10, 0, 10),
              child: Text("${model!.appVersion} • ${model!.appSize} MB"),
            )
          ],
        ));
  }
}
