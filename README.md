#ice-demo2
===============

content:
- study ice
- 试着将ICE和SPRING集成


##常用命令

slice2java --output-dir ../generated  HelloWorld.ice


启动server（最好用IDE启动）
java IceBox.Server --Ice.Config=config.properties



启动注册中心
icegridregistry --Ice.Config=D:\repository\ice-demo2-master\src\main\resources\registry.cfg


#启动node1
icegridnode --Ice.Config=D:\repository\ice-demo2-master\src\main\resources\node1.cfg
#启动node2
icegridnode --Ice.Config=D:\repository\ice-demo2-master\src\main\resources\node2.cfg

#管理服务
#可视化管理
#启动icegridgui.jar 将app.xml放入其中（服务可以任意放到node1或是node2中），在工具里将服务启动起来，在客户端配置注册中心然后发起远端调用

#命令行管理
icegridadmin --Ice.Config=D:\repository\ice-demo2-master\src\main\resources\registry.cfg
application add D:\repository\ice-demo2-master\src\main\resources\app.xml
application list
server start PrinterServer


常见问题：
如果报找不到主类，首先检查class是否存在，然后检查target下依赖包lib生成没有，若没有则运行一下 mvn package，使其生成。