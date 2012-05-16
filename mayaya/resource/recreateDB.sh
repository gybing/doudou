#!/bin/sh

DB_HOST=localhost
DB_PORT=3306
DB_USER=mayaya
DB_PWD=mayaya@mayaya.com

for file in `find /root/chaogao/database/sql/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done

for file in `find /root/chaogao/database/init_data/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done

for file in `find /root/chaogao/database/test_data/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done

for file in `find /root/chaogao/database/TFLH_data/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done

for file in `find /root/chaogao/database/mel_test_data/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done

for file in `find /root/chaogao/database/etonKids/ -type f -name "*.sql"`;do
	echo $file;
	`mysql -u${DB_USER} -p${DB_PWD} -h${DB_HOST} -P${DB_PORT} --default-character-set=utf8 < ${file}`
done