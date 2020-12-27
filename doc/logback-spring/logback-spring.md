# 记录logback遇到的问题

## 问题：

​		初期使用的log配置文件名为`logback.xml`，但是在配置中定义的应用名称变量总是出现`APP_NAME_IS_UNDEFINED`，经过查询发现是加载顺序不同，正确顺序如下：

```
logback和logback-spring.xml都可以用来配置logback，但是2者的加载顺序是不一样的。

logback.xml--->application.properties--->logback-spring.xml
```

这样就是先加载application主配置，然后获取`spring.application.name`变量的值

```yml
spring:
  application:
    name: young-mall
```

生成的文件在工程根目录：

```
logs/young-mall/young-mall.2020-12-27.0.log
```





## logback-spring.xml详细配置：

```xml

<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--引用默认日志配置-->
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <!--使用默认的控制台日志输出实现-->
    <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>
    <!--应用名称-->
    <springProperty scope="context" name="APP_NAME" source="spring.application.name" defaultValue="springBoot"/>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>[%d{yyyy-MM-dd HH:mm:ss.SSS}][%thread][%level][%logger{35}] %msg %n
            </pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>
    <appender name="FILE"
              class="ch.qos.logback.core.rolling.RollingFileAppender">

        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>logs/${APP_NAME}/${APP_NAME}.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>50MB</maxFileSize>
            <maxHistory>180</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>[%d{yyyy-MM-dd HH:mm:ss.SSS}][%thread][%level][%logger{35}] %msg %n
            </pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="CONSOLE"></appender-ref>
        <appender-ref ref="FILE"></appender-ref>
    </root>
</configuration>

```





