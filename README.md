j360-datasource
===============

## 构建 ##
- Java 1.7+
- Maven 3.2+
- Spring 4.1.3


## 1.0功能介绍 ##
- 当前版本说明
    - 分表：基于配置的count通过loadbalance算法映射到对应的table_0 table_1 ... table_{count}
    - 分库：多数据源切换，通过loadbalance算法映射到对应的datasource
    - 读写分离：基于配置的write数据源、read数据库类型，匹配到对应的数据源类型
    - 多服务器轮训：基于lb算法中的RoundRobin算法轮训多个数据源分流，此处和分库最大的区别在于服务器的高可用分流
- jdbc框架测试：ibatis
    

## 2.0升级说明 ##
- 后续版本升级方向
    - 自定义负载均衡算法，更新权重，将权重数据缓存计算
    - 更细每次都要去计算tablehash和databasehash的效率，将配置统一缓存使用缓存计算
    - 新增JavaConfig方式，新增spring boot集成和动态数据更新
    - 新增多重jdbc、orm框架支持 mybatis、hibernate、spring jdbc等
