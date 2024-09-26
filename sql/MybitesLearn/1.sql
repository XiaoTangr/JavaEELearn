create database d_mybatislearn;
use d_mybatislearn;
create table t_users(
                        uid int primary key auto_increment,
                        uname varchar(20) not null,
                        uage int not null,
                        usex int not null
);
insert into t_users
(uid,uname,uage,usex)
values
    (10001,"张三",19,1),
    (10002,"李四",25,1),
    (10003,"王美",21,0),
    (10004,"张梅",19,0);