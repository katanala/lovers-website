#!/bin/bash

# 查找运行的项目
pid=`ps -ef | grep java | grep -v grep | grep 'nest.jar' | awk '{print $2}'`

# 结束运行的项目
if [ "$pid" == "" ]; then
    echo "项目未运行"
else
    echo "进程：$pid"
    kill -9 $pid
    echo "项目已关闭"
fi

# 部署新项目
if [ ! -f "/home/nest.jar" ];then
    echo "不需要更新项目"
else
    echo "需要更新项目，删除原项目"
    rm -f /home/jar_nest/nest.jar
    echo "原项目已删除，部署新项目"
    mv /home/nest.jar /home/jar_nest/nest.jar
fi

echo "启动项目中..."

# 启动程序
nohup java -jar /home/jar_nest/nest.jar > /home/nest.log 2>&1 &

echo "项目已启动，打开运行日志"

tail -200f nest.log
