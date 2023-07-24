import 'package:flutter/material.dart';
import 'package:my_flutter/route/routeMethods.dart';

class CustomAppBar extends AppBar {
  CustomAppBar(this.content, {super.key});

  final pressModel = CounterModel();
  final String content;

  @override
  Widget? get leading => IconButton(
      onPressed: () {
        pressModel.onBackPress();
      },
      icon: const Icon(Icons.arrow_back_ios_new));

  @override
  Widget? get title => Text(
        content,
        style: const TextStyle(fontSize: 18),
      );

  @override
  // TODO: implement centerTitle
  bool? get centerTitle => true;
}

class SettingItem extends StatelessWidget {
  final String item;
  final VoidCallback onPressed;

  const SettingItem({super.key, required this.item, required this.onPressed});

  @override
  Widget build(BuildContext context) {
    return Container(
        padding:
            const EdgeInsets.only(left: 20, top: 16, right: 16, bottom: 16),
        child: GestureDetector(
          onTap: onPressed,
          child: Row(
            children: [
              Text(
                item,
                style: const TextStyle(fontSize: 16, color: Colors.black87),
              ),
              const Expanded(child: Text("")),
              const Icon(Icons.chevron_right)
            ],
          ),
        ));
  }
}
