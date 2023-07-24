import 'package:flutter/material.dart';
import 'package:my_flutter/route/routeMethods.dart';

import 'CustomViews.dart';

class AboutMeWidget extends StatelessWidget {
  const AboutMeWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(useMaterial3: true),
      home: const AboutMePage(),
    );
  }
}

class AboutMePage extends StatelessWidget {
  const AboutMePage({super.key});

  @override
  Widget build(BuildContext context) {
    final pressModel = CounterModel();

    return Scaffold(
      appBar: CustomAppBar("关于我们"),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const Padding(
            padding: EdgeInsets.only(top: 30, bottom: 8),
            child: Image(
              image: AssetImage('assets/ic_launcher.png'),
              width: 120,
              height: 120,
            ),
          ),
          FutureBuilder<String>(
            future: pressModel.getAppVersionName(),
            builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                // 数据正在加载中
                return const CircularProgressIndicator();
              } else if (snapshot.hasError) {
                // 发生错误时
                return const Text('v.0.0');
              } else {
                // 成功获取到数据
                final String data = snapshot.data ?? 'v.0.0';
                return Text(
                  data,
                  style: const TextStyle(fontSize: 12, color: Colors.black45),
                );
              }
            },
          ),
          const Padding(padding: EdgeInsets.only(top: 40)),
          SettingItem(
              item: "隐私政策",
              onPressed: () {
                pressModel.showToast("隐私政策");
              }),
          SettingItem(
              item: "用户协议",
              onPressed: () {
                pressModel.showToast("用户协议");
              }),
          SettingItem(
              item: "免责声明",
              onPressed: () {
                pressModel.showToast("免责声明");
              }),
          SettingItem(
              item: "版本升级",
              onPressed: () {
                pressModel.showToast("当前已是最新版");
              }),
          const Expanded(child: Text("")),
          const Padding(
            padding: EdgeInsets.only(bottom: 16),
            child: Text("阑尾科技有限公司",
                style: TextStyle(fontSize: 12, color: Colors.black26)),
          )
        ],
      ),
    );
  }
}
