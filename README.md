## 项目简介
本项目基于SpringCloud实现一套微服务治理框架，包含微服务治理相关的服务组件，集成服务注册发现、链路追踪、日志收集、灰度发布、RPC调用等能力。
## 包含项目
| 项目名称| 说明| 技术方案|
|--- | --- | --- |
| 网关服务| 微服务入口，集成限流/认证等能力| SpringCloudGateway、 JWT、 令牌桶算法|
|注册发现 | 提供服务注册中心、服务注册客户端lib ,支持多环境| SpringCloudAlibaba, Nacos | 
|配置中心 | 提供微服务配置能力，支持多环境配置|Nacos
| 链路追踪| 提供调用链路追踪，提供server和client lib| zipkin-server 、SpringCloudSleuth
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
