## 2-1 使用Eureka搭建注册中心

- Maven两大核心功能：依赖管理（Jar包管理）、构建项目（项目打包）
- 

注册中心的配置文件   
eureka:   
application.properties   
```
# 获取注册信息（因为自身是注册中心，所以不需要获取）
eureka.client.fetch-registry=false
# 向注册中心注册自己（因为自身是注册中心，所以不需要进行注册）
eureka.client.register-with-eureka=false
```

单应用的配置：
```
# 获取注册信息（默认为true）
eureka.client.fetch-registry=true
# 向注册中心注册自己（默认为true）
eureka.client.register-with-eureka=true
# 注册中心地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```


网关的配置（网关也属于单应用服务）：
gateway:application.properties
```
spring.cloud.gateway.routes[0].id=system
spring.cloud.gateway.routes[0].uri=http://127.0.0.1:9001
spring.cloud.gateway.routes[0].predicates[0].name=path
spring.cloud.gateway.routes[0].predicates[0].args[0]=/system/**
```
