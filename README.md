j360-datasource
===============

## 构建 ##
- Java 1.7+
- Maven 3.2+
- Spring 4.1.3

## 1.0介绍 ##
- 当前版本说明
    - 分表分库、读写分离应用层框架

## 2.0升级说明 ##
- 后续版本升级方向
    - 自定义负载均衡算法，更新权重，将权重数据缓存计算
    - 更细每次都要去计算tablehash和databasehash的效率，将配置统一缓存使用缓存计算
    - 新增JavaConfig方式，新增spring boot集成和动态数据更新
    - 新增多重jdbc、orm框架支持 mybatis、hibernate、spring jdbc等
