## 项目简介
本项目基于SpringCloud实现一套微服务治理框架，包含微服务治理相关的服务组件，集成服务注册发现、链路追踪、日志收集、灰度发布、RPC调用等能力。
## 包含项目
| 项目名称|module| 说明| 技术方案|
|--- |--- | --- | --- |
| 网关服务 |lura-gateway-server| 微服务入口，集成限流、鉴权能力| SpringCloudGateway、 JWT、 令牌桶算法|
|微服务jar包|lura-framework-spring-boot-starter | 提供服务注册中心、服务注册客户端lib ,支持多环境| SpringCloudAlibaba, Sentinel, nacos, zipkin | 
|nacos服务 |lura-nacos-server| 提供微服务配置能力，支持多环境配置|Nacos、docker-compose
| zipkin服务|lura-zipkin-server |提供调用链路追踪| zipkin-server 、docker-compose
|框架核心代码|lura-framework-core | 提供框架上下文，能力封装 | SpringCloudSleuth| 
|认证服务|lura-framework-auth-server|用户认证服务，生成JWT token|SpringSecurity、JWT|

## 提供能力
| 能力| 说明| 
|--- | --- | 
| 网关服务| 微服务入口，集成限流/认证等能力|
|注册发现 | 提供服务注册中心、服务注册客户端lib ,支持多环境|
|配置中心 | 提供微服务配置能力，支持多环境配置|
| 链路追踪| 提供调用链路追踪，提供server和client lib| 
| 灰度发布 | 提供微服务灰度发布策略，rpc灰度调用、网关灰度转发|


## 本地构建
### 本地启停基础设施方法
#### 整体启动
启动所有的基础设施服务
```shell
bash ./scripts/start.sh
```

#### 分布启动
1. 创建本地docker网络
```shell
bash ./scripts/start-net.sh
```
2. 启动nacos服务
```shell
bash ./scripts/stop-nacos-server.sh
```
3. 启动zipkin服务
```shell
bash ./scripts/start-zipkin-server.sh
```

## 技术架构图

## FAQ
