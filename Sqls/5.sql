USE d_mybiteslearn;

# select
CREATE TABLE tb_worker
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(32),
    age       INT,
    sex       VARCHAR(8),
    worker_id INT UNIQUE
);
INSERT INTO tb_worker(name, age, sex, worker_id)
VALUES ('张三', 32, '女', 1001),
       ('李四', 28, '男', 1002),
       ('王五', 35, '女', 1003),
       ('赵六', 40, '男', 1004);