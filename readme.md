# 测试平台
## Docker 环境配置
- redis
```shell
# 创建卷保存redis的持久化数据
docker volume create --driver local test-platform-redis-volume
# 使用自定义配置文件
# docker run -d --name test-platform-redis -v test-platform-redis-volume:/data -v /myredis/conf:/usr/local/etc/redis redis:6 redis-server --save 60 1 --loglevel warning
# 不使用自定义文件
docker run -d --name test-platform-redis -v test-platform-redis-volume:/data redis:6 redis-server --save 60 1 --loglevel warning
# 客户端
docker run -it --rm --link test-platform-redis:serverlink redis:6 redis-cli -h serverlink
# 进入容器
docker exec -it test-platform-redis bash
```
- mysql
```shell
# 创建卷保存mysql的持久化数据
docker volume create --driver local test-platform-mysql-volume
# 使用自定义配置文件
# docker run -d --name test-platform-mysql -v test-platform-mysql-volume:/var/lib/mysql -v /my/custom:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=root123 mysql:5.7
# 不适用自定义文件
docker run -d --name test-platform-mysql -v test-platform-mysql-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root123 mysql:5.7
# 客户端
docker run -it --rm --link test-platform-mysql:serverlink mysql:5.7 mysql -hserverlink -uroot -p
# 进入容器
docker exec -it test-platform-mysql bash
# 查看帮助
docker run -it --rm mysql:5.7 --verbose --help
```
- APP
```shell
# 构建镜像
docker build -t test-platform:latest .
# 运行容器
docker run -d -p 8080:8888 --name test-platform-app --link test-platform-mysql:mysql --link test-platform-redis:redis test-platform:latest
# 查看日志
docker logs -f test-platform-app
```