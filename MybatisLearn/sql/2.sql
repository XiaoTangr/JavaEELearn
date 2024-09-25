use d_mybatislearn;
CREATE TABLE t_students
(
    sid   INT PRIMARY KEY AUTO_INCREMENT,
    sname VARCHAR(50),
    sage  INT
);
INSERT INTO t_students(sname, sage)
VALUES ('张三', 25);
INSERT INTO t_students(sname, sage)
VALUES ('李四', 21);
INSERT INTO t_students(sname, sage)
VALUES ('王五', 20);
