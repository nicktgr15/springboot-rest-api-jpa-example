

start_mysql:
	docker run --rm --name mysql5 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tid -e MYSQL_ROOT_HOST="172.17.0.1" -d mysql/mysql-server:5.7

mysql_terminal:
	mysql -u root -p -h 127.0.0.1