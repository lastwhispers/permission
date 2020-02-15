#!/bin/bash
# 还原数据库
mysql -uroot -proot -P 3306 < permission.sql

# 清空redis
cd /usr/local/redis-5/bin/
db=0
#可以用第一个参数指定需求清除的库
if [ -n "$1" ];then
        db=$1
fi

./redis-cli -h 127.0.0.1 -p 6379 -a root <<END
select ${db}
flushdb
END
