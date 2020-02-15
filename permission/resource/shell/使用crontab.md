# crontab -e写入如下内容
```shell script
0 */12 * * * /root/mysqldump.sh  # 每隔12个小时备份一次数据库
0 */2 * * * /root/permission.sh # 每隔两个小时还原一次数据库和redis
```
# crontab的几个服务命令
service crond start             //启动服务
service crond stop             //关闭服务
service crond restart         //重启服务
service crond reload         //重新载入配置