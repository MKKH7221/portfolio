FROM mysql

EXPOSE 3306

ADD ./conf.d/my.cnf /etc/mysql/conf.d/my.cnf

#docker runに実行される
CMD ["mysqld"]