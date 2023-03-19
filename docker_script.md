# Docker scripts

## mysql

`docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d -p 3307:3306 mysql/mysql-server:8.0.32`

### How to connect mysql running in the container

1. Connect using mysql client directly to the mysql in docker: `docker exec -it mysql57 mysql -uroot -p`
(If this is a fresh installation you will be asked to change the password using ALTER USER command. Do it.)

2. Run SQL: `update mysql.user set host = '%' where user='root';`

3. Quit the mysql client.

4. Restart the container: `docker restart mysql`
5. Run sql in mysql to check: `select host, user from mysql.user;`

## rabbitmq

`docker pull rabbitmq:3.11.10-management`

`docker run --name myrabbitmq -p 15672:15672 -p 5672:5672 -d rabbitmq:management
`
    - using default user and password: guest/guest

` docker run --name rabbitmq -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=root -d rabbitmq:3.11.10-management`
    - customize user and password
