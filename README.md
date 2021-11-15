## 2-1 使用Eureka搭建注册中心

- Maven两大核心功能：依赖管理（Jar包管理）、构建项目（项目打包）
- 

eureka:
application.properties
```
# 获取注册信息（因为自身是注册中心，所以不需要获取）
eureka.client.fetch-registry=false
# 向注册中心注册自己（因为自身是注册中心，所以不需要进行注册）
eureka.client.register-with-eureka=false
```

```
# 获取注册信息（默认为true）
eureka.client.fetch-registry=true
# 向注册中心注册自己（默认为true）
eureka.client.register-with-eureka=true
# 注册中心地址
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```