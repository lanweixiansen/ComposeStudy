import 'package:flutter/material.dart';
import 'package:my_flutter/route/routeMethods.dart';

class NormalWidget extends StatelessWidget {
  const NormalWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        useMaterial3: true,
      ),
      home: const NormalPage(title: '基本布局'),
    );
  }
}

class NormalPage extends StatelessWidget {
  const NormalPage({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    final pressModel = CounterModel();
    const TextStyle titleTextStyle = TextStyle(
        fontSize: 20, fontWeight: FontWeight.bold, color: Colors.black);
    BoxDecoration boxDecoration = BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.black, width: 1));

    final TextEditingController _controller = TextEditingController();

    void _onSubmitted(String value) {
      pressModel.showToast("搜索内容: $value");
    }

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
        body: SingleChildScrollView(
            child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              margin: const EdgeInsets.only(top: 16, left: 16),
              child: const Text(
                "文字",
                style: titleTextStyle,
              ),
            ),
            Container(
              margin: const EdgeInsets.all(16),
              padding: const EdgeInsets.all(8),
              decoration: boxDecoration,
              child: const Text(
                "这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本，这是一段文本。",
                maxLines: 2,
                textAlign: TextAlign.center,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(fontSize: 16, color: Colors.black54),
              ),
            ),
            Container(
              margin: const EdgeInsets.only(left: 16),
              child: const Text(
                "混合文本",
                style: titleTextStyle,
              ),
            ),
            Container(
              margin: const EdgeInsets.all(16),
              padding: const EdgeInsets.all(8),
              decoration: boxDecoration,
              child: const Text.rich(TextSpan(children: [
                TextSpan(text: "这是一段混合文本，"),
                TextSpan(text: "红色文字，", style: TextStyle(color: Colors.red)),
                TextSpan(
                    text: "绿色粗体文字，",
                    style: TextStyle(
                        color: Colors.green, fontWeight: FontWeight.bold)),
                TextSpan(text: "混合在一起。")
              ])),
            ),
            Container(
              margin: const EdgeInsets.only(left: 16),
              child: const Text(
                "图片",
                style: titleTextStyle,
              ),
            ),
            Container(
              margin: const EdgeInsets.all(16),
              padding: const EdgeInsets.all(8),
              decoration: boxDecoration,
              child: FadeInImage.assetNetwork(
                  fit: BoxFit.fill,
                  placeholder: 'assets/image_loading.gif',
                  image:
                      "https://img2.baidu.com/it/u=2712563041,2003488332&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"),
            ),
            Container(
              margin: const EdgeInsets.only(left: 16),
              child: const Text(
                "输入框",
                style: titleTextStyle,
              ),
            ),
            Container(
              width: double.infinity,
              margin: const EdgeInsets.only(left: 16, right: 16, top: 8),
              padding: const EdgeInsets.all(8),
              decoration: boxDecoration,
              child: TextField(
                controller: _controller,
                decoration:
                    const InputDecoration(hintText: "输入内容", labelText: "文本"),
                keyboardType: TextInputType.text,
                textInputAction: TextInputAction.search,
                onSubmitted: _onSubmitted,
              ),
            ),
          ],
        )));
  }
}
