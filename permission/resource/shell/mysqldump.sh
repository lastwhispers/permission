#! /bin/bash
echo "mysqldump start ..."
mysqldump -uroot -proot -P 3306 permission > /root/backformysql/DB_permission_`date +%Y_%m_%d_%H_%M_%s`.sql
#导出数据库结构mysqldump -uroot -d 数据库名> test.sql
#导出表结构mysqldump -uroot -d 数据库名 表名> test.sql
echo "mysqldump success ok !"
