# 项目简介
提供微服务认证能力，通过用户登陆，获取jwt token。


# 说明

## 服务注册组使用框架 LURA-FRAMEWORK

```yaml
spring:
  cloud:
    nacos:
      discovery:
        group: LURA-FRAMEWORK
```

## 默认登陆用户

使用内存认证器：inMemoryAuthentication，默认用户名`user`,密码`123456`

```java
 auth.inMemoryAuthentication()
        .passwordEncoder(NoOpPasswordEncoder.getInstance())
        .withUser("user")
        .password("123456")
        .authorities("ROLE_USER");
```

## 默认token加密方式
使用MacSigner进行token加密，key: securityKey

## 获取token接口

```shell
curl -X POST \
  http://localhost:8013/auth/login \
  -H 'content-type: application/json' \
  -d '{"username":"user","password":"123456"}'
```
