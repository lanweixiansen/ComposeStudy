class RouteBean {
  String title;
  String route;
  String image;

  RouteBean({required this.title, required this.route, required this.image});
}

final routeList = List.of([
  RouteBean(title: "IOS 布局仿写", route: "ios_page", image: 'assets/image/img1.jpg'),
  RouteBean(title: "基本控件", route: "normal_widget", image: 'assets/image/img2.jpg'),
  RouteBean(title: "list_title", route: "list_title", image: 'assets/image/img3.jpg'),
  RouteBean(title: "anim_logo", route: "logo_app", image: 'assets/image/img4.jpg'),
]);
