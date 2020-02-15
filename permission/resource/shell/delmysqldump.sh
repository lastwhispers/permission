#! /bin/bash
find /root/backformysql/ -mtime +7  -name "*.sql" -exec rm -rf {} \;
