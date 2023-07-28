import 'package:flutter/material.dart';
import 'package:my_flutter/route/routeMethods.dart';
import 'AboutMe.dart';
import 'CustomViews.dart';

class SettingWidget extends StatelessWidget {
  const SettingWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      routes: {
        "about_me": (context) => const AboutMeWidget(),
      },
      theme: ThemeData(
        useMaterial3: true,
      ),
      home: const SettingPage(title: '设置'),
    );
  }
}

class SettingPage extends StatelessWidget {
  const SettingPage({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    final pressModel = CounterModel();

    return Scaffold(
      appBar: CustomAppBar("设置"),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SettingItem(
              item: "通知管理",
              onPressed: () {
                pressModel.showToast("通知管理");
              }),
          SettingCacheItem(model: pressModel),
          SettingItem(
              item: "权限管理",
              onPressed: () {
                pressModel.showToast("权限管理");
              }),
          SettingItem(
              item: "关于我们",
              onPressed: () {
                Navigator.pushNamed(context, "about_me");
              }),
          const Expanded(
            flex: 4,
            child: Text(""),
          ),
          LogoutItem(onPressed: () {
            pressModel.logout();
          }),
          const Expanded(
            flex: 1,
            child: Text(""),
          )
        ],
      ),
    );
  }
}

class SettingCacheItem extends StatefulWidget {
  const SettingCacheItem({super.key, required this.model});

  final CounterModel model;

  @override
  State<StatefulWidget> createState() => _CacheState();
}

class _CacheState extends State<SettingCacheItem> {
  void clearCache() {
    setState(() {
      widget.model.clearCache();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        padding:
            const EdgeInsets.only(left: 20, top: 16, right: 16, bottom: 16),
        child: GestureDetector(
          onTap: () {
            clearCache();
          },
          child: Row(
            children: [
              const Text(
                "清除缓存",
                style: TextStyle(fontSize: 16, color: Colors.black87),
              ),
              const Expanded(child: Text("")),
              FutureBuilder<String>(
                future: widget.model.getCacheSize(),
                builder:
                    (BuildContext context, AsyncSnapshot<String> snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    // 数据正在加载中
                    return const CircularProgressIndicator();
                  } else if (snapshot.hasError) {
                    // 发生错误时
                    return const Text('0.00KB');
                  } else {
                    // 成功获取到数据
                    final String data = snapshot.data ?? '0.00KB';
                    return Text(
                      data,
                      style:
                          const TextStyle(fontSize: 14, color: Colors.black45),
                    );
                  }
                },
              ),
              const Icon(Icons.chevron_right)
            ],
          ),
        ));
  }
}

class LogoutItem extends StatelessWidget {
  const LogoutItem({super.key, required this.onPressed});

  final VoidCallback onPressed;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: TextButton(
        style: ButtonStyle(
            backgroundColor: MaterialStateProperty.all(Colors.black),
            shape: MaterialStateProperty.all(RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12.0))),
            padding: MaterialStateProperty.all(const EdgeInsets.only(
                left: 100, top: 16, bottom: 16, right: 100))),
        onPressed: onPressed,
        child: const Text(
          "退出登陆",
          style: TextStyle(
              fontSize: 18, color: Colors.white, fontWeight: FontWeight.bold),
        ),
      ),
    );
  }
}
