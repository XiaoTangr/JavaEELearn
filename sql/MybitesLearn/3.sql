USE d_mybiteslearn;
CREATE TABLE t_customers (
                            id int(32) PRIMARY KEY AUTO_INCREMENT,
                            username varchar(50),
                            jobs varchar(50),
                            phone varchar(16));
INSERT INTO t_customers VALUES ('10001', '张三', 'teacher', '18320001111');
INSERT INTO t_customers VALUES ('10002', '王五', 'teacher', '18320002222');
INSERT INTO t_customers VALUES ('10003', '刘九', 'worker', '18320003333');
