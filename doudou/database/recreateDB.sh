#!/bin/sh

DB_HOST=localhost
DB_PORT=3306
DB_USER=mayaya
DB_PWD=mayaya@mayaya.com

for file in `find /home/chaogao/doudou/database/sql/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done

for file in `find /home/chaogao/doudou/database/init_data/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done

for file in `find /home/chaogao/doudou/database/test_data/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done
