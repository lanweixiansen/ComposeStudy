class RouteBean {
  String title;
  String route;

  RouteBean({required this.title, required this.route});
}

final routeList = List.of([
  RouteBean(title: "IOS 布局仿写", route: "ios_page"),
  RouteBean(title: "基本控件", route: "normal_widget")
]);
