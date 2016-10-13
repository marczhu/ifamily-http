

# 模块介绍
ifamily-server http服务，提供put、get、list、clear服务，链接如下:  
```
http://127.0.0.1/ifamily/put/?key=abc
http://127.0.0.1/ifamily/get/?key=abc
http://127.0.0.1/ifamily/list
http://127.0.0.1/ifamily/clear
```

ifamily-server-runner 内嵌jetty容器，加载ifamily-server.war,目前还有日志问题未解决    
ifamily-agent 发送ip  
ifamily-client 获取ip  