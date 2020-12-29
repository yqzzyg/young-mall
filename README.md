# young-mall

## 介绍
此项目采用`SpringBoot`、`Spring Security`、`Mybatis`、`Redis`等建立起来的一整套商城，后端管理系统采用Vue，用户端采用微信小程序。用于把最近学习的东西融合到一块，未来将持续更新，后续补全文档。

## 项目文档
后台系统Swagger-ui文档：https://youngqz.cn/mall-admin/doc.html

前台系统Swagger-ui文档：https://youngqz.cn/mall-client/doc.html

## 项目目录

```
mall
├── doc	---------------->笔记
├── young-admin -------->后台系统
├── young-client-api --->前台系统
├── young-common ------->工具类及通用代码  
├── young-db ----------->MyBatisGenerator生成的数据库操作代码
└── young-security ----->SpringSecurity封装公用模块
```

## 环境搭建

| 工具         | 说明                | 官网                                                    |
| ------------ | ------------------- | ------------------------------------------------------- |
| IDEA         | 开发IDE             | https://www.jetbrains.com/idea/download                 |
| RedisDesktop | redis客户端连接工具 | https://github.com/qishibo/AnotherRedisDesktopManager   |
| finalshell   | Linux远程连接工具   | http://www.hostbuf.com/downloads/finalshell_install.exe |
| Navicat      | 数据库连接工具      | http://www.formysql.com/xiazai.html                     |
| Snipaste     | 屏幕截图工具        | https://www.snipaste.com/                               |
| Postman      | API接口调试工具     | https://www.postman.com/                                |
| Typora       | Markdown编辑器      | https://typora.io/                                      |

##  开发环境

| 工具  | 版本号 | 下载                                                         |
| ----- | ------ | ------------------------------------------------------------ |
| JDK   | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Mysql | 5.7    | https://www.mysql.com/                                       |
| Redis | 5.0    | https://redis.io/download                                    |
| Nginx | 1.10   | http://nginx.org/en/download.html                            |

### 搭建步骤

### 1、windows环境

只需要clone项目到本地，确保开发环境正确，直接运行即可



### 2、linux环境

1)、本地执行maven命令：`clean install -pl young-common,young-db,young-security -am`

2)、把`jar`包上传到`linux`服务器

3)、执行脚本（脚本在）项目的`doc/deploy/sh`目录下

​	把`jar`包分别拷贝到`young-mall-admin`和`young-mall-client`目录下

​	分别执行`admin-start.sh`和`client-start.sh`脚本即可

4)、查看日志，日志在 `/young-mal/${APP_NAME}/logs/${APP_NAME}/` 目录下

```
 tailf young-mall-admin.2020-12-29.0.log 
```

3、jenkins环境部署

目前jenkins部署只是简单的使用Jenkins远程打包，把jar包发送到目标机器，并执行脚本，其实就是把`linux环境`部署的步骤实现自动化

具体配置查看`doc/deploy/jenkins-deploy.md`文档

##  致谢

1. [ dts-mall](https://gitee.com/qiguliuxing/dts-shop)

   项目参考：

   	1. young-mall项目数据库基于dts-mall项目数据库
    	2. 后端代码以及前端代码参考dts-mall
    	3. 替换原项目的`shiro`为`Spring Security`

2. [mall-admin-web](https://github.com/macrozheng/mall-admin-web)

   项目介绍：mall-admin-web是一个电商后台管理系统的前端项目，基于Vue+Element实现。

   项目参考：young-mall项目的young-admin模块的一些页面布局样式参考了mall-admin-web项目。

## 许可证

[MIT License](https://gitee.com/young-mall/young-mall/blob/master/LICENSE)