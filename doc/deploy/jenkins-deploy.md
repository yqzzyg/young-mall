# 使用jenkins部署后端工程

使用jenkins拉取gitee代码，打包为jar，然后把jar包移动到指定目录，使用SSH远程执行脚本启动工程。

## 一、建立jenkins执行任务

![1](png\1.png)



### 1.1 然后在源码管理中添加git仓库地址

![2](png\2.png)

### 2.2 添加构建

之后我们需要添加一个构建，选择调用顶层maven目标，该构建主要用于把我们的源码打包成Docker镜像并上传到我们的Docker镜像仓库去：

![3](png\3.png)



### 3.3 设置maven

选择maven版本，然后设置maven命令和指定pom文件位置：

![4](png\4.png)

由于使用了多Module工程，所有需要先install其他Module，防止启动时缺少依赖而失败。

![5](png\5.png)



### 3.4 使用脚本把jar包发送到指定的目录

![6](png\6.png)

```
Name : 选择刚才系统设置里配置的连接。

Source files : Jenkins打好的war包路径（该路径是相对路径，相对于Jenkins目录下的workspace）

Remove prefix :忽略的路径前缀。比如Source files 里填写的是 young-admin/target/*.jar , Remove prefix里填写young-admin/target/，那么复制war包时就不会生成young-admin/target/目录

Remote directory : 相对于系统设置里的Remote directory。jar包发送到远程服务器的位置。

Exec command : 可以填写命令，也可以是一个脚本。 会在jar包发送成功执行。
```

**脚本内容：**

`admin-stop.sh`

```sh
BASE_DIR=/usr/local/mydata/young-mall
cd $BASE_DIR/young-mall-admin
sh $BASE_DIR/young-mall-admin/admin-boot.sh stop
```

`admin-start.sh`

```sh
BASE_DIR=/usr/local/mydata/young-mall
cd $BASE_DIR/young-mall-admin
sh $BASE_DIR/young-mall-admin/admin-boot.sh start
```

其中`admin-boot.sh`

```sh
#!/bin/bash
JAR_NAME=young-admin-0.0.1-SNAPSHOT.jar
#PID  代表是PID文件
PID=$API_NAME\.pid

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $JAR_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0     
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then 
    echo ">>> ${JAR_NAME} is already running PID=${pid} <<<" 
  else 
    nohup $JRE_HOME/bin/java -Xms256m -Xmx1024m -jar $JAR_NAME >/dev/null 2>&1 &
    echo $! > $PID
    echo ">>> start $JAR_NAME successed PID=$! <<<" 
   fi
  }

#停止方法
stop(){
  #is_exist
  pidf=$(cat $PID)
  #echo "$pidf"  
  echo ">>> api PID = $pidf begin kill $pidf <<<"
  kill $pidf
  rm -rf $PID
  sleep 2
  is_exist
  if [ $? -eq "0" ]; then 
    echo ">>> api 2 PID = $pid begin kill -9 $pid  <<<"
    kill -9  $pid
    sleep 2
    echo ">>> $JAR_NAME process stopped <<<"  
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi  
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> ${JAR_NAME} is running PID is ${pid} <<<"
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi
}

#重启
restart(){
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
exit 0
```



### 3.5  之后点击保存按钮，任务创建成功

![7](png\7.png)

## 二、复制任务

![8](png\8.png)

**可以根据admin工程复制出一个新的client工程**