

# 模块介绍
ifamily-server http服务用于获取agent的ip地址，提供put、get、list、clear服务，链接如下:  
```
http://127.0.0.1/ifamily/put/?key=abc
http://127.0.0.1/ifamily/get/?key=abc
http://127.0.0.1/ifamily/list
http://127.0.0.1/ifamily/clear
```

ifamily-server-runner 内嵌jetty容器，加载ifamily-server.war,目前还有日志问题未解决(ifamily-server-runner中有log配置，使用runner启动jetty，jetty加载的ifamily-server也有log配置，貌似要使用MDC，暂时先不解决)
ifamily-agent 客户端，访问ifamily-server,启动方法:  
bin/start.sh 定时发送put,使用配置文件中的默认key  
bin/start.sh start key=xxx 定时发送put，使用指定的key  

bin/start.sh put 执行put，使用配置文件中的默认key  
bin/start.sh put key=xxx  执行put，使用指定的key  

bin/start.sh get 执行get，使用配置文件中的默认key  
bin/start.sh get key=xxx 执行get，使用指定的key  

bin/start.sh list 执行list  
bin/start.sh clear 执行clear  
